package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Bids createBids(BidsDTO bidsDTO){
        User foundUser = userRepository.findById(bidsDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("User does not exist!"));
        Auction foundAuction = auctionRepository.findById(bidsDTO.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Auction does not exist!"));

        Bids newBids = new Bids();
        newBids.setAuction(foundAuction);
        newBids.setUser(foundUser);

        if (bidsDTO.getPrice() <= foundAuction.currentPrice){
            throw new RuntimeException("Your bid cannot be lower than " + foundAuction.currentPrice + " the current bid.");
        } else {
            foundAuction.setCurrentPrice(bidsDTO.getPrice());
        }

        if (foundUser.getBalance() < bidsDTO.getPrice()){
            throw new RuntimeException("Your bid cannot be higher than your balance. Your current balance is " + foundUser.getBalance() + "Your current bid is " + bidsDTO.getPrice() + ".");
        }else {
            foundUser.setBalance(foundUser.getBalance() - bidsDTO.getPrice());
        }

        if (foundAuction.isHasBids() == false){
            foundAuction.setHasBids(true);
        }
        userRepository.save(foundUser);
        auctionRepository.save(foundAuction);
        return bidsRepository.save(newBids);
    }

//Find a bids by id
    public Bids findOne(BidsDTO bidsDTO){
        return bidsRepository.findById(bidsDTO.).get();
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
