package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Account;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.repositories.AccountRepository;
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
    AccountRepository accountRepository;


    public Bids createBids(Bids bids){
        Account foundUser = accountRepository.findById(bids.getUserId())
                .orElseThrow(()-> new RuntimeException("User does not exist!"));
        Auction foundAuction = auctionRepository.findById(bids.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Auction does not exist!"));
        bids.setAuction(foundAuction);
        bids.setAccount(foundUser);

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

        if (foundAuction.isHasBids() == false){
            foundAuction.setHasBids(true);
        }

        accountRepository.save(foundUser);
        auctionRepository.save(foundAuction);
        return bidsRepository.save(bids);
    }

    public List<Bids>findAllBids(){
        return bidsRepository.findAll();
    }

    public Bids findOne(String id){
        return bidsRepository.findById(id).get();
    }



    public Bids updateBids(Bids bids) {
        Account foundUser = accountRepository.findById(bids.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        Auction foundAuction = auctionRepository.findById(bids.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        bids.setAuction(foundAuction);
        bids.setAccount(foundUser);
        return bidsRepository.save(bids);
    }



    public String deleteBids(String id){
        bidsRepository.deleteById(id);
        return "Deleted successfully!";
    }

}
