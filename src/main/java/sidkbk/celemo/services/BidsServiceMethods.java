package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
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
    static AuctionRepository auctionRepository;

    @Autowired
    static UserRepository userRepository;
    public static Bids bidPriceCheck(BidsDTO bidsDTO, Bids newBid){
        if (bidsDTO.getMaxBid() == 0) {
            newBid.setMaxPrice(newBid.getStartPrice());
            bidsDTO.setMaxBid(bidsDTO.getStartBid());
        } else {
            newBid.setMaxPrice(bidsDTO.getMaxBid());
        }
        return newBid;
    }
    public static User foundUserCheck(BidsDTO bidsDTO){
        // gets DTO, checks user from user-repo
        User foundUser = userRepository.findById(bidsDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("User does not exist!"));
        return foundUser;
    }
    public static Auction foundAuctionCheck(BidsDTO bidsDTO){
        Auction foundAuction = auctionRepository.findById(bidsDTO.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Auction does not exist!")); // This might be a problem
        return foundAuction;
    }
    public static void checkAuctionOwner(Auction foundAuction, User foundUser){
        Optional<User> auctionOwner = userRepository.findById(foundAuction.getSeller());
        if (foundUser.getUsername().equals(auctionOwner.get().getUsername())) {
            throw new RuntimeException("You can't bid on your own auction");
        }
    }
    public static void bidOkCheck(BidsDTO bidsDTO, Auction foundAuction, User foundUser){
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
}
