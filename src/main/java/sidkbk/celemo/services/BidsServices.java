package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.BidsDTO;
import sidkbk.celemo.models.User;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.repositories.UserRepository;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;

import java.util.List;

@Service
public class BidsServices {

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;



// Find all bids
    public List<Bids>findAllBids(){
        return bidsRepository.findAll();
    }




    // Create a bids using price, userId and listingId
    public ResponseEntity<?> createBids(BidsDTO bidsDTO){
        // gets DTO, checks user from user-repo
        User foundUser = userRepository.findById(bidsDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("User does not exist!"));
        // gets DTO, checks auction id from auction-repo
        Auction foundAuction = auctionRepository.findById(bidsDTO.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Auction does not exist!"));

        // makes new bid object
        Bids newBid = new Bids();
        // sets user found from DTO ID
        newBid.setUser(foundUser);
        // sets auction from found bid on auction which might try to find the bid from the auction and the auction has the bid
        // newBid.setAuction(foundAuction);

        newBid.setStartPrice(bidsDTO.getStartPrice());
        newBid.setAuctionId(bidsDTO.getAuctionId());
        newBid.setMaxPrice(bidsDTO.getMaxPrice());

        // Checks if users balance is valid
        if (bidsDTO.getMaxPrice() > foundUser.getBalance()){
            throw new RuntimeException("Your max bid can not be higher than " + foundUser.getBalance() + " , your current balance.");
        }
        // Checks if users balance is less than starting bid
        if (foundUser.getBalance() < newBid.getStartPrice()){
            throw new RuntimeException("Your bid cannot be higher than your balance. Your current balance is " + foundUser.getBalance() + "Your current bid is " + bidsDTO.getStartPrice() + ".");
        }

        // user loses
            // checks if auction has a bid
        if (foundAuction.isHasBids() == true){
            // checks if user has the same id as the previous user
            if (!foundAuction.getBid().getUser().getId().equals(newBid.getUser().getId())) {
                Bids auctionCurrentBid = bidsRepository.findById(foundAuction.getBid().getId()).get();

                User currentBidUser = userRepository.findById(auctionCurrentBid.getUser().getId()).get();

                // checks if new bid is less than the current
                if (newBid.getMaxPrice() < auctionCurrentBid.getMaxPrice()) {
                    // Raises by 10 if possible
                    if (newBid.getMaxPrice() + 10 <= auctionCurrentBid.getMaxPrice()) {
                        auctionCurrentBid.setCurrentPrice(newBid.getMaxPrice() + 10);
                    } else {
                        auctionCurrentBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                    }
                    bidsRepository.save(auctionCurrentBid);
                    foundAuction.setCurrentPrice(auctionCurrentBid.getCurrentPrice());
                    auctionRepository.save(foundAuction);
                    return ResponseEntity.ok(newBid.getMaxPrice() + " is less than auctions current bids max price.");
                }
                // if the bids are equal sets the previous/current bid as winner.
                if (newBid.getMaxPrice() == auctionCurrentBid.getMaxPrice()) {
                    auctionCurrentBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                    foundAuction.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                    bidsRepository.save(auctionCurrentBid);
                    auctionRepository.save(foundAuction);
                    return ResponseEntity.ok(newBid.getMaxPrice() + " is as much as the auctions current bids max price. Make a new bid if you want to continue. New price is previous bids max");
                }
                // user wins
                // Checks if you can raise the current price by ten if not still wins
                if (newBid.getMaxPrice() > auctionCurrentBid.getMaxPrice()) {
                    if (auctionCurrentBid.getMaxPrice() + 10 < newBid.getMaxPrice()) {
                        newBid.setCurrentPrice(auctionCurrentBid.getCurrentPrice() + 10);
                        bidsRepository.save(newBid);
                        foundAuction.setCurrentPrice(newBid.getCurrentPrice());
                        currentBidUser.setBalance(auctionCurrentBid.getMaxPrice());
                        foundAuction.setBid(newBid);
                        auctionRepository.save(foundAuction);
                        userRepository.save(currentBidUser);
                        foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
                        userRepository.save(foundUser);
                        return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");
                    }else {
                        newBid.setCurrentPrice(auctionCurrentBid.getCurrentPrice());
                        bidsRepository.save(newBid);
                        foundAuction.setBid(newBid);
                        foundAuction.setCurrentPrice(newBid.getCurrentPrice());
                        foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
                        userRepository.save(foundUser);
                        auctionRepository.save(foundAuction);
                        return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");
                    }
                }
            } else {
                return ResponseEntity.ok("You can't bid twice in a row.");
            }

            //send back balance of lost bids
        }else if (!foundAuction.isHasBids()){
            newBid.setCurrentPrice(newBid.getStartPrice());
            userRepository.save(foundUser);
            bidsRepository.save(newBid);
            foundAuction.setBid(newBid);
            foundAuction.setCurrentPrice(newBid.getCurrentPrice());
            foundAuction.setHasBids(true);
            auctionRepository.save(foundAuction);
            return ResponseEntity.ok(" Has been created, current price is " + newBid.getCurrentPrice());
        }

        return ResponseEntity.ok("Something went wrong");
    }





//Find a bids by id
    public Bids findOne(String id){
        return bidsRepository.findById(id).get();
    }

// delete a bids
    public String deleteBids(String id){
        bidsRepository.deleteById(id);
        return "Deleted successfully!";
    }

// update a bids
    public Bids updateBids(BidsDTO bidsDTO) {
        User foundUser = userRepository.findById(bidsDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        Auction foundAuction = auctionRepository.findById(bidsDTO.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        Bids newUpdate = new Bids();
        newUpdate.setAuctionId(foundAuction.getId());
        newUpdate.setUser(foundUser);
        return bidsRepository.save(newUpdate);
    }


}
