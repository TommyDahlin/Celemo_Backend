package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.User;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.repositories.AccountRepository;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;

import java.util.List;

@Service
public class AuctionService {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BidsRepository bidsRepository;

    public Auction createAuction(Auction auction) {
        User findUser = accountRepository.findById(auction.getSellerId())
                .orElseThrow(() -> new RuntimeException("Couldn't find user."));
        auction.setAccount(findUser);
        return auctionRepository.save(auction);
    }
    // READ ALL
    public List<Auction> getAllAuctions(){
        return auctionRepository.findAll();
    }

    // READ 1
    public Auction getOneAuction(String id){
       return auctionRepository.findById(id).get();
    }
    // PUT
    public Auction updateAuction(Auction auction){
        return auctionRepository.save(auction);
    }
    // DELETE 1 by id
    public String deleteAuction(String id) {
        auctionRepository.deleteById(id);
        return "Deleted successfully!";
    }
    // Delete all to drop clean collection remotely (only for testing don't keep to production)
    public void deleteAllAuctions(){
        auctionRepository.deleteAll();
    }
}
