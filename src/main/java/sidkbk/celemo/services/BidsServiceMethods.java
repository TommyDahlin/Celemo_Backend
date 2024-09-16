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
public class BidsServiceMethods {

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
     UserRepository userRepository;


    public static Bids bidMaxPriceCheck(BidsDTO bidsDTO, Bids newBid){
        if (bidsDTO.getMaxBid() == 0) {
            newBid.setMaxPrice(newBid.getStartPrice());
            bidsDTO.setMaxBid(bidsDTO.getStartBid());
        } else {
            newBid.setMaxPrice(bidsDTO.getMaxBid());
        }
        return newBid;
    }

    public void checkAuctionOwner(Auction foundAuction, User foundUser){
        Optional<User> auctionOwner = userRepository.findById(foundAuction.getSeller());
        if (foundUser.getUsername().equals(auctionOwner.get().getUsername())) {
            throw new RuntimeException("You can't bid on your own auction");
        }
    }

    public void bidOkCheck(BidsDTO bidsDTO, Auction foundAuction, User foundUser){
        // Check if startBid and maxBid is higher than auction startPrice
        if (bidsDTO.getMaxBid() <= foundAuction.getStartPrice()) {
            throw new RuntimeException("Your bids cannot be the same or lower than auctions starting price or current price.");
        }

        // Checks if users balance is valid
        if (bidsDTO.getMaxBid() > foundUser.getBalance()){
            throw new RuntimeException("Your max bid can not be higher than " + foundUser.getBalance() + " , your current balance.");
        }

        // Checks if users balance is less than starting bid
        if (bidsDTO.getStartBid() > foundUser.getBalance()){
            throw new RuntimeException("Your bid cannot be higher than your balance. Your current balance is "
                    + foundUser.getBalance() + "Your current bid is " + bidsDTO.getStartBid() + ".");
        }
    }
    public int bidWinCheck(Bids auctionCurrentBid, Bids newBid){
        // This is the case determinator to check the prices of the current bid and users new bid
        int caseNmr = 0;
        // User Loses because his max price is less than the current bids max price.
        if (auctionCurrentBid.getMaxPrice() > newBid.getMaxPrice()){
            caseNmr = 1;
            // elif for if they have the same price.
        } else if (auctionCurrentBid.getMaxPrice() == newBid.getMaxPrice()) {
            caseNmr = 2;
            // case for if the new bid wins.
        }else if (auctionCurrentBid.getMaxPrice() < newBid.getMaxPrice()){
            caseNmr = 3;
        }
        return caseNmr;
    }
    public ResponseEntity<?> userLoses(Bids newBid, Bids auctionCurrentBid, Auction foundAuction){
        Bids updatedBid = auctionCurrentBid;
        // Method for telling the user that his bid lost, and his balance is not changed.
            // Raises by 10 if possible
            if (newBid.getMaxPrice() + 10 <= auctionCurrentBid.getMaxPrice()) {
                updatedBid.setCurrentPrice(newBid.getMaxPrice() + 10);
            } else {
                updatedBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
            }
            bidsRepository.save(newBid);
            bidsRepository.save(updatedBid);
            foundAuction.setCurrentPrice(updatedBid.getCurrentPrice());
            foundAuction.setBid(updatedBid.getId());
            foundAuction.setCounter(foundAuction.getCounter() + 1);
            auctionRepository.save(foundAuction);
            return ResponseEntity.ok(newBid.getMaxPrice() + " is less than auctions current bids max price. New current bid is: " + foundAuction.currentPrice);

    }
    public ResponseEntity<?> userMatchesBid(Bids newBid, Bids auctionCurrentBid, Auction foundAuction){
        // Method for telling the user that his bid matched, current bid on the auction wins ,and his balance is not changed.
        Bids updatedBid = auctionCurrentBid;
        auctionCurrentBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());

        foundAuction.setCurrentPrice(auctionCurrentBid.getMaxPrice());
        foundAuction.setBid(updatedBid.getId());
        foundAuction.setCounter(foundAuction.getCounter() + 1);
        bidsRepository.save(newBid);
        bidsRepository.save(updatedBid);
        auctionRepository.save(foundAuction);
        return ResponseEntity.ok(newBid.getMaxPrice() + " is as much as the auctions current " + foundAuction.currentPrice + " bids max price. Make a new bid if you want to continue. New price is previous bids max");

    }
    public ResponseEntity<?> userWins(Bids newBid, Bids auctionCurrentBid, Optional<User> currentBidUser, Auction foundAuction, User foundUser){
        // Method for telling the user that his bid won, and his balance is changed.
        if (auctionCurrentBid.getMaxPrice() + 10 < newBid.getMaxPrice()) {
            // Sets currentprice on the new bid to previous bid + 10
            newBid.setCurrentPrice(auctionCurrentBid.getMaxPrice() + 10);
            // Sets auctions current price
            foundAuction.setCurrentPrice(newBid.getCurrentPrice());
            // Gives back balance from previous winning bid user
            currentBidUser.get().setBalance(currentBidUser.get().getBalance() + auctionCurrentBid.getMaxPrice());
            // sets auctions bid to newBids id
            foundAuction.setBid(newBid.getId());
            // increases counter by 1
            foundAuction.setCounter(foundAuction.getCounter() + 1);
            checkBidBeforeSave(newBid);
            // Saves newBid
            bidsRepository.save(newBid);
            // Saves auction
            auctionRepository.save(foundAuction);
            // saves currentBidUser to save balance.
            userRepository.save(currentBidUser.get());
            // Changes balance from new winner of bid.
            foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
            // Saves user that won.
            userRepository.save(foundUser);
            return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");

        }
        else {
            // Your bid was higher but if you can't do +10 currency you still win and gets put to max price.
            newBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());

            foundAuction.setCurrentPrice(newBid.getMaxPrice());
            currentBidUser.get().setBalance((currentBidUser.get().getBalance() + auctionCurrentBid.getMaxPrice()));
            foundAuction.setBid(newBid.getId());
            foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());

            checkBidBeforeSave(newBid);

            bidsRepository.save(newBid);
            userRepository.save(currentBidUser.get());
            userRepository.save(foundUser);
            foundAuction.setCounter(foundAuction.getCounter() + 1);
            auctionRepository.save(foundAuction);
            return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");

        }
    }
    public void noPreviousBidsWin(User foundUser, Bids newBid, Auction foundAuction){
        // Method for telling the user that his bid won because no previous bids were on the auction, and his balance is changed.
        foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
        newBid.setCurrentPrice(foundAuction.getCurrentPrice() + 10);
        foundAuction.setBid(newBid.getId());
        // auction price gets set directly from first startprice instead of a method that i use if there's already a bid.
        foundAuction.setCurrentPrice(foundAuction.getCurrentPrice() + 10);
        foundAuction.setHasBids(true);
        foundAuction.setCounter(foundAuction.getCounter() + 1);
        bidsRepository.save(newBid);
        userRepository.save(foundUser);
        auctionRepository.save(foundAuction);
    }
    public void checkBidBeforeSave(Bids newBid){
        if (newBid.getUser() == null || newBid.getAuctionId() == null || newBid.getStartPrice() == 0.0 || newBid.getMaxPrice() == 0.0 || newBid.getCurrentPrice() == 0.0) {
            ResponseEntity.ok("Something is null wrong. checkBidBeforeSave()");
        }
    }
}