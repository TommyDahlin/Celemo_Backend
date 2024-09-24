package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.Bids.BidsDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.Optional;

@Service
public class BidsServiceHelper {

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;


    public static Bids bidMaxPriceCheck(BidsDTO bidsDTO, Bids newBid) {
        if (bidsDTO.getMaxBid() == 0) {
            // if maxbid is empty sets it to startprice.
            newBid.setMaxPrice(newBid.getStartPrice());
            bidsDTO.setMaxBid(bidsDTO.getStartBid());
        } else {
            newBid.setMaxPrice(bidsDTO.getMaxBid());
        }
        return newBid;
    }

    // bid has to go through all these checks to be eligible to be saved.
    public void checkAuctionOwner(Auction foundAuction, User foundUser) {
        if (foundUser.getId().equals(foundAuction.getSeller())) {
            throw new RuntimeException("You can't bid on your own auction");
        }
    }
    public void checkFinished(Auction foundAuction) {
        if (foundAuction.isFinished()) {
            throw new RuntimeException("You can't bid on a finished auction.");
        }
    }
    public void startPriceCheck(BidsDTO bidsDTO, Auction foundAuction) {
        // Check if startBid and maxBid is higher than auction startPrice
        if (bidsDTO.getMaxBid() <= foundAuction.getStartPrice()) {
            throw new RuntimeException("Your bids cannot be the same or lower than auctions starting price or current price.");
        }
    }
        public void checkUserBalance(User foundUser, BidsDTO bidsDTO){
        // Checks if users balance is valid
        if (bidsDTO.getMaxBid() > foundUser.getBalance() || bidsDTO.getStartBid() > foundUser.getBalance()) {
            throw new RuntimeException("Your bid lack sufficient funds. \n Your current balance " + foundUser.getBalance() + " Your start bid: " + bidsDTO.getStartBid() + "\n Your max bid: " + bidsDTO.getMaxBid());
        }
    }
    public void checkBidBeforeSave(Bids newBid){
        if (newBid.getId() == null || newBid.getUser() == null || newBid.getAuctionId() == null || newBid.getStartPrice() == 0.0 || newBid.getMaxPrice() == 0.0 || newBid.getCurrentPrice() == 0.0) {
            ResponseEntity.ok("Something is null wrong. checkBidBeforeSave()");
        }
    }

    public int bidWinCheck(Bids auctionCurrentBid, Bids newBid) {
        // This is the case determinator to check the prices of the current bid and users new bid
        int caseNmr = 0;
        // User Loses because his max price is less than the current bids max price.
        if (auctionCurrentBid.getMaxPrice() >= newBid.getMaxPrice()) {
            caseNmr = 1;
            // case for if the new bid wins.
        } else if (auctionCurrentBid.getMaxPrice() < newBid.getMaxPrice()) {
            caseNmr = 2;
        }
        return caseNmr;
    }

    public ResponseEntity<?> userLoses(Bids newBid, Bids auctionCurrentBid, Auction foundAuction) {
        Bids updatedBid = auctionCurrentBid;
        // Message variable to change what it says depending on if you match or lose bid.
        String message;
        // Raises by 10 if possible
        if (newBid.getMaxPrice() + 10 < auctionCurrentBid.getMaxPrice()) {
            updatedBid.setCurrentPrice(newBid.getMaxPrice() + 10);
            message = " is less than auctions current bids max price. New current bid is: ";
        } else if (newBid.getMaxPrice() == auctionCurrentBid.getMaxPrice()) {
            updatedBid.setCurrentPrice(newBid.getMaxPrice());
            message = " is the same as the current max bid, previous bidder wins. Current Price is:  ";
        } else {
            updatedBid.setCurrentPrice(newBid.getMaxPrice());
            message = " is less than auctions current bids max price. New current bid is: ";
        }
        bidsRepository.save(updatedBid);
        foundAuction.setCurrentPrice(updatedBid.getCurrentPrice());
        foundAuction.setBid(updatedBid.getId());
        foundAuction.setCounter(foundAuction.getCounter() + 1);
        bidsRepository.save(newBid);
        auctionRepository.save(foundAuction);
        return ResponseEntity.ok(newBid.getMaxPrice() + message + foundAuction.currentPrice);
    }

    public ResponseEntity<?> userWins(Bids newBid, Bids auctionCurrentBid, Optional<User> currentBidUser, Auction foundAuction, User foundUser) {
        // Method for telling the user that his bid won, and his balance is changed.
        // This checks that everything in the bid has a value
        if (auctionCurrentBid.getMaxPrice() + 10 < newBid.getMaxPrice()) {
            // Sets currentprice on the new bid to previous bid + 10
            newBid.setCurrentPrice(auctionCurrentBid.getMaxPrice() + 10);
        } else {
            // Your bid was higher but if you can't do +10 currency you still win and gets put to max price.
            newBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
        }
        // Sets auctions current price
        checkBidBeforeSave(newBid);
        bidsRepository.save(newBid);
        foundAuction.setCurrentPrice(newBid.getCurrentPrice());
        // Gives back balance from previous winning bid user
        currentBidUser.get().setBalance(currentBidUser.get().getBalance() + auctionCurrentBid.getMaxPrice());
        // sets auctions bid to newBids id
        foundAuction.setBid(newBid.getId());
        // increases counter by 1
        foundAuction.setCounter(foundAuction.getCounter() + 1);
        // Changes balance from new winner of bid.
        foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
        userRepository.save(currentBidUser.get());
        userRepository.save(foundUser);
        auctionRepository.save(foundAuction);
        return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");
    }
    public ResponseEntity<?> noPreviousBidsWin(User foundUser, Bids newBid, Auction foundAuction){
        // Method for telling the user that his bid won because no previous bids were on the auction, and his balance is changed.
        foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
        newBid.setCurrentPrice(foundAuction.getCurrentPrice() + 10);
        // auction price gets set directly from first startprice instead of a method that i use if there's already a bid.
        bidsRepository.save(newBid);
        checkBidBeforeSave(newBid);
        foundAuction.setBid(newBid.getId());
        foundAuction.setCurrentPrice(newBid.getCurrentPrice());
        foundAuction.setHasBids(true);
        foundAuction.setCounter(foundAuction.getCounter() + 1);
        userRepository.save(foundUser);
        auctionRepository.save(foundAuction);
        return ResponseEntity.ok("Current price is: " + foundAuction.currentPrice);
    }

}