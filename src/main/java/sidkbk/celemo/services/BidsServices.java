package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Reviews;
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


    public Bids createBids(Bids bids){
        User foundUser = userRepository.findById(bids.getUserId())
                .orElseThrow(()-> new RuntimeException("User does not exist!"));
        Auction foundAuction = auctionRepository.findById(bids.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Auction does not exist!"));
        bids.setAuction(foundAuction);
        bids.setUser(foundUser);

        if (bids.getPrice() <= foundAuction.currentPrice){
            throw new RuntimeException("Your bid cannot be lower than " + foundAuction.currentPrice + " the current bid.");
        } else {
            foundAuction.setCurrentPrice(bids.getPrice());
        }

        if (foundUser.getBalance() < bids.getPrice()){
            throw new RuntimeException("Your bid cannot be higher than your balance. Your current balance is " + foundUser.getBalance() + "Your current bid is " + bids.getPrice() + ".");
        }else {
            foundUser.setBalance(foundUser.getBalance() - bids.getPrice());
        }

        if (!foundAuction.isHasBids()){
            foundAuction.setHasBids(true);
        }

        userRepository.save(foundUser);
        auctionRepository.save(foundAuction);
        return bidsRepository.save(bids);
    }

    public List<Bids>findAllBids(){
        return bidsRepository.findAll();
    }

    public Bids findOne(String id) {
        Bids foundBid = bidsRepository.findById(id).orElseThrow(() -> new RuntimeException("Bid not found"));
        return foundBid;
    }


    public Bids updateBids(Bids bids) {
        User foundUser = userRepository.findById(bids.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        Auction foundAuction = auctionRepository.findById(bids.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        bids.setAuction(foundAuction);
        bids.setUser(foundUser);
        return bidsRepository.save(bids);
    }


    public String deleteBids(String id) {
        bidsRepository.findById(id).orElseThrow(() -> new RuntimeException("Bid does not exists!"));
        bidsRepository.deleteById(id);
        return "Bid deleted!";
    }

}
