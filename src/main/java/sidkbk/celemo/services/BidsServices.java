package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
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
        User foundUser = userRepository.findById(bidsDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("User does not exist!"));

        Auction foundAuction = auctionRepository.findById(bidsDTO.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Auction does not exist!"));

        Bids auctionCurrentBid = bidsRepository.findById(foundAuction.bidId).get();

        User currentBidUser = userRepository.findById(auctionCurrentBid.getId()).get();

        Bids newBid = new Bids();
        newBid.setUser(foundUser);
        newBid.setAuction(foundAuction);
        newBid.setStartPrice(bidsDTO.getStartPrice());
        newBid.setMaxPrice(bidsDTO.getMaxPrice());
        newBid.setStartPrice(newBid.getStartPrice());

        // Checks if users balance is valid
        if (newBid.getMaxPrice() < foundUser.getBalance()){
            throw new RuntimeException("Your max bid can not be higher than " + foundUser.getBalance() + " , your current balance.");
        }
        if (foundUser.getBalance() < newBid.getStartPrice()){
            throw new RuntimeException("Your bid cannot be higher than your balance. Your current balance is " + foundUser.getBalance() + "Your current bid is " + bidsDTO.getStartPrice() + ".");
        }

        // user loses
        if (foundAuction.isHasBids()){
            // checks if new bid is less than the current
            if (newBid.getMaxPrice() < auctionCurrentBid.getMaxPrice()){
                // Raises by 10 if possible
                if (newBid.getMaxPrice() + 10 <= auctionCurrentBid.getMaxPrice()){
                    auctionCurrentBid.setCurrentPrice(newBid.getMaxPrice() + 10);
                    bidsRepository.save(auctionCurrentBid);
                    foundAuction.setCurrentPrice(auctionCurrentBid.getCurrentPrice());
                    auctionRepository.save(foundAuction);
                    return ResponseEntity.ok(newBid.getMaxPrice() + " is less than auctions current bids max price.");
                }else {
                    auctionCurrentBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                    bidsRepository.save(auctionCurrentBid);
                    foundAuction.setCurrentPrice(auctionCurrentBid.getCurrentPrice());
                    auctionRepository.save(foundAuction);
                    return ResponseEntity.ok(newBid.getMaxPrice() + " is less than auctions current bids max price.");
                }
            }
            // if the bids are equal sets the current bid as winner.
            if (newBid.getMaxPrice() == auctionCurrentBid.getMaxPrice()){
                auctionCurrentBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                foundAuction.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                bidsRepository.save(auctionCurrentBid);
                auctionRepository.save(foundAuction);
                return ResponseEntity.ok(newBid.getMaxPrice() + " is as much than auctions current bids max price. Make a new bid if you want to continue. New price is previous bids max");
            }
            // user wins
            // Checks if you can raise the current price by ten if not still wins
            if (newBid.getMaxPrice() > auctionCurrentBid.getMaxPrice()) {
                if (auctionCurrentBid.getMaxPrice() + 10 < newBid.getMaxPrice()){
                    newBid.setCurrentPrice(auctionCurrentBid.getCurrentPrice() + 10);
                    bidsRepository.save(newBid);
                    foundAuction.setCurrentPrice(newBid.getCurrentPrice());
                    currentBidUser.setBalance(auctionCurrentBid.getMaxPrice());
                    auctionRepository.save(foundAuction);
                    userRepository.save(currentBidUser);
                    foundUser.setBalance(newBid.getCurrentPrice());
                    userRepository.save(foundUser);
                    return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");
                }
                newBid.setCurrentPrice(auctionCurrentBid.getCurrentPrice());
                bidsRepository.save(newBid);
                foundAuction.setCurrentPrice(newBid.getCurrentPrice());
                auctionRepository.save(foundAuction);
                return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");
            }

            //send back balance of lost bids
        }
        foundAuction.setHasBids(true);

        return ResponseEntity.ok(newBid + " Has been created, current price is" + newBid.getCurrentPrice());
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
        newUpdate.setAuction(foundAuction);
        newUpdate.setUser(foundUser);
        return bidsRepository.save(newUpdate);
    }


}
