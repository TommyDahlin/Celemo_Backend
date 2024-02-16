package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.repository.AuctionRepository;

import java.util.List;

@Service
public class AuctionService {
    @Autowired
    AuctionRepository auctionRepository;

    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }
    // READ ALL
    public List<Auction> getAllAuctions(){
        return auctionRepository.findAll();
    }
    // READ 1
    public Auction getOneBook(String id){
       return auctionRepository.findById(id).get();
    }
    // PUT
    public Auction updateAuction(Auction auction){
        return auctionRepository.save(auction);
    }
    // DELETE 1
    public String deleteAuction(String id) {
        auctionRepository.deleteById(id);
        return "Deleted successfully!";
    }
}
