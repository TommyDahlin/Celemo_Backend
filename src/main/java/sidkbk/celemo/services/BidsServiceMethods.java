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


    public static Bids bidPriceCheck(BidsDTO bidsDTO, Bids newBid){
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
        if (bidsDTO.getStartBid() <= foundAuction.getStartPrice() || bidsDTO.getMaxBid() <= foundAuction.getStartPrice() || bidsDTO.getStartBid() <= foundAuction.getCurrentPrice() + 10) {
            throw new RuntimeException("Your bids cannot be lower than auctions starting price or current price.");
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
        int caseNmr = 0;
        if (auctionCurrentBid.getMaxPrice() > newBid.getMaxPrice()){
            caseNmr = 1;
        } else if (auctionCurrentBid.getMaxPrice() == newBid.getMaxPrice()) {
            caseNmr = 2;
        }else if (auctionCurrentBid.getMaxPrice() < newBid.getMaxPrice()){
            caseNmr = 3;
        }
        return caseNmr;
    }
    public ResponseEntity<?> userLoses(Bids newBid, Bids auctionCurrentBid, Bids updatedBid, Auction foundAuction){
        if (newBid.getMaxPrice() < auctionCurrentBid.getMaxPrice()) {
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
    }
        return ResponseEntity.ok(newBid.getMaxPrice() + " is less than auctions current bids max price. New current bid is: " + foundAuction.currentPrice);
    }
    public ResponseEntity<?> userMatchesBid(Bids newBid, Bids auctionCurrentBid, Bids updatedBid, Auction foundAuction){
        auctionCurrentBid.setCurrentPrice(auctionCurrentBid.getMaxPrice());
        foundAuction.setCurrentPrice(auctionCurrentBid.getMaxPrice());
        bidsRepository.save(newBid);
        bidsRepository.save(updatedBid);
        foundAuction.setBid(updatedBid.getId());
        foundAuction.setCounter(foundAuction.getCounter() + 1);
        auctionRepository.save(foundAuction);
    return ResponseEntity.ok(newBid.getMaxPrice() + " is as much as the auctions current " + foundAuction.currentPrice + " bids max price. Make a new bid if you want to continue. New price is previous bids max");
    }
    public ResponseEntity<?> userWins(Bids newBid, Bids auctionCurrentBid, Optional<User> currentBidUser, Auction foundAuction, User foundUser){
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
        }
        return ResponseEntity.ok(newBid.getCurrentPrice() + " you have the current bid.");
    }
    public void noPreviousBidsWin(User foundUser, Bids newBid, Auction foundAuction){
        foundUser.setBalance(foundUser.getBalance() - newBid.getMaxPrice());
        userRepository.save(foundUser);
        newBid.setCurrentPrice(newBid.getStartPrice());
        bidsRepository.save(newBid);
        foundAuction.setBid(newBid.getId());
        foundAuction.setCurrentPrice(newBid.getCurrentPrice());
        foundAuction.setHasBids(true);
        foundAuction.setCounter(foundAuction.getCounter() + 1);
        auctionRepository.save(foundAuction);
    }
}