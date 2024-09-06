package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.Bids.BidsDTO;
import sidkbk.celemo.dto.Bids.FindBidIdDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidsServices {

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;

    BidsServiceMethods bidsMethods;


// Find all bids
    public List<Bids>findAllBids(){
        return bidsRepository.findAll();
    }


    // Create a bids using price, userId and listingId
    public ResponseEntity<?> createBids(BidsDTO bidsDTO){

        // gets DTO, checks user from user-repo
        User foundUser = bidsMethods.foundUserCheck(bidsDTO);
        // gets DTO, checks auction id from auction-repo
        Auction foundAuction = bidsMethods.foundAuctionCheck(bidsDTO);
        // auction owner check
        bidsMethods.checkAuctionOwner(foundAuction, foundUser);



        // makes new bid object
        Bids newBid = new Bids();
        // sets user found from DTO ID
        newBid.setUser(foundUser.getId());
        newBid.setStartPrice(bidsDTO.getStartBid());
        newBid.setAuctionId(bidsDTO.getAuctionId());

        bidsMethods.bidPriceCheck(bidsDTO, newBid);

        bidsMethods.bidOkCheck(bidsDTO, foundAuction, foundUser);
        // checks if auction has a bid
        if (foundAuction.isHasBids() && foundAuction.getBid() != null){

            // checks if user has the same id as the previous user
            if (!foundAuction.getSeller().equals(newBid.getUser())) {

                Bids auctionCurrentBid = bidsRepository.findById(foundAuction.getBid()).get();

                Optional<User> currentBidUser = userRepository.findById(auctionCurrentBid.getUser());

                Bids updatedBid = new Bids();
                updatedBid.setUser(auctionCurrentBid.getUser());
                updatedBid.setAuctionId(auctionCurrentBid.getAuctionId());
                updatedBid.setStartPrice(auctionCurrentBid.getStartPrice());
                updatedBid.setMaxPrice(auctionCurrentBid.getMaxPrice());


                // user loses
                // checks if new bid is less than the current
                if (newBid.getMaxPrice() < auctionCurrentBid.getMaxPrice()) {
                    // Raises by 10 if possible
                    if (newBid.getMaxPrice() + 10 <= auctionCurrentBid.getMaxPrice()) {
                        //auctionCurrentBid.setCurrentPrice(newBid.getMaxPrice() + 10);
                        updatedBid.setCurrentPrice(newBid.getMaxPrice() + 10);
                    } else {
                        updatedBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                    }

                    bidsRepository.save(newBid);
                   // bidsRepository.save(auctionCurrentBid);
                    bidsRepository.save(updatedBid);
                    foundAuction.setCurrentPrice(updatedBid.getCurrentPrice());
                    foundAuction.setBid(updatedBid.getId());
                    foundAuction.setCounter(foundAuction.getCounter() + 1);
                    auctionRepository.save(foundAuction);
                    return ResponseEntity.ok(newBid.getMaxPrice() + " is less than auctions current bids max price. New current bid is: " + foundAuction.currentPrice);
                }

                // if the bids are equal sets the previous/current bid as winner.
                if (newBid.getMaxPrice() == auctionCurrentBid.getMaxPrice()) {
                    auctionCurrentBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                    foundAuction.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                    bidsRepository.save(newBid);
                    bidsRepository.save(updatedBid);
                    foundAuction.setBid(updatedBid.getId());
                    foundAuction.setCounter(foundAuction.getCounter() + 1);
                    auctionRepository.save(foundAuction);
                    return ResponseEntity.ok(newBid.getMaxPrice() + " is as much as the auctions current " + foundAuction.currentPrice + " bids max price. Make a new bid if you want to continue. New price is previous bids max");
                }
                // user wins
                // Checks if you can raise the current price by ten if not still wins
                if (newBid.getMaxPrice() > auctionCurrentBid.getMaxPrice()) {

                    if (auctionCurrentBid.getMaxPrice() + 10 < newBid.getMaxPrice()) {
                        newBid.setCurrentPrice(auctionCurrentBid.getMaxPrice() + 10);
                        bidsRepository.save(newBid);
                        foundAuction.setCurrentPrice(newBid.getCurrentPrice());
                        currentBidUser.get().setBalance(currentBidUser.get().getBalance() + auctionCurrentBid.getMaxPrice());
                        foundAuction.setBid(newBid.getId());
                        foundAuction.setCounter(foundAuction.getCounter() + 1);
                        auctionRepository.save(foundAuction);
                        userRepository.save(currentBidUser.get());
                        foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
                        userRepository.save(foundUser);
                        return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");
                    }
                    else {
                        // if you cant do 10+ monies to your maxbid
                        newBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
                        bidsRepository.save(newBid);

                        foundAuction.setCurrentPrice(newBid.getMaxPrice());
                        currentBidUser.get().setBalance((currentBidUser.get().getBalance() + auctionCurrentBid.getMaxPrice()));
                        foundAuction.setBid(newBid.getId());
                        foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
                        userRepository.save(currentBidUser.get());
                        userRepository.save(foundUser);
                        foundAuction.setCounter(foundAuction.getCounter() + 1);
                        auctionRepository.save(foundAuction);
                        return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");
                    }
                }
            } else {
                return ResponseEntity.ok("You can't bid twice in a row.");
            }

            //send back balance of lost bids
        }else {

            foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
            userRepository.save(foundUser);

            newBid.setCurrentPrice(newBid.getStartPrice());
            bidsRepository.save(newBid);

            foundAuction.setBid(newBid.getId());
            foundAuction.setCurrentPrice(newBid.getCurrentPrice());
            foundAuction.setHasBids(true);
            foundAuction.setCounter(foundAuction.getCounter() + 1);
            auctionRepository.save(foundAuction);
            return ResponseEntity.ok("Bid has been created, current price is " + newBid.getCurrentPrice());
        }
        return ResponseEntity.ok("Something went wrong");
    }





//Find a bids by id
    public Bids findOne(String bidId){
        return bidsRepository.findById(bidId).get();
    }

// delete a bids
    public String deleteBids(FindBidIdDTO findBidIdDTO){
        bidsRepository.deleteById(findBidIdDTO.getbidId());
        return "Deleted successfully!";
    }

// update a bid
    public Bids updateBids(BidsDTO bidsDTO) {
        User foundUser = userRepository.findById(bidsDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        Auction foundAuction = auctionRepository.findById(bidsDTO.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        Bids newUpdate = new Bids();
        newUpdate.setAuctionId(foundAuction.getId());
        newUpdate.setUser(foundUser.getId());
        return bidsRepository.save(newUpdate);
    }

    public List<Bids> findAllBidsForUser(String userId){
        // Find user
        userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        // Skapa en tom lista för hittade bids
        List<Bids> foundBids = new ArrayList<>();
        // Spara alla bids i en lista
        List<Bids> allBids = bidsRepository.findAll();
        // For loop igenom alla bids och kolla efter bids som matchar med userid
        for (Bids bids : allBids){
            if (bids.getUser() != null && bids.getUser().equals(userId)) {
                foundBids.add(bids);
            }
        }
        return foundBids;
    }

    public List<Bids> findByAuction(String auctionId) {
        return bidsRepository.findBidsByAuctionId(auctionId);
    }
}
