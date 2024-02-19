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
                .orElseThrow(()-> new RuntimeException("User dose not exist!"));
        Auction foundAuction = auctionRepository.findById(bids.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Auction dose not exist!"));
        bids.setAuction(foundAuction);
        bids.setAccount(foundUser);
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
                .orElseThrow(() -> new RuntimeException("User dose not exist!"));
        Auction foundAuction = auctionRepository.findById(bids.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction dose not exist!"));
        bids.setAuction(foundAuction);
        bids.setAccount(foundUser);
        return bidsRepository.save(bids);
    }



    public String deleteBids(String id){
        bidsRepository.deleteById(id);
        return "Deleted successfully!";
    }

}
