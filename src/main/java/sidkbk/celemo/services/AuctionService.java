package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.models.User;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.repositories.UserRepository;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;

import java.util.List;

@Service
public class AuctionService {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BidsRepository bidsRepository;

    public Auction createAuction(Auction auction) {
        User foundUser = userRepository.findById(auction.getSellerId()).orElseThrow(() -> new RuntimeException("User not found!"));
        auction.setUser(foundUser);

        return auctionRepository.save(auction);
    }
    // READ ALL
    public List<Auction> getAllAuctions(){
        return auctionRepository.findAll();
    }

    // READ 1

    public Auction getOneAuction(String id) {
        Auction foundAuction = auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("Auction not found"));
        return foundAuction;
    }
    // PUT
    public Auction updateAuction(String reviewId, Auction updatedAuction) {
        return auctionRepository.findById(reviewId)
                .map(existingAuction -> {
                    if (updatedAuction.getTitle() != null) {
                        existingAuction.setTitle(updatedAuction.getTitle());
                    }
                    if (updatedAuction.getProductDescription() != null) {
                        existingAuction.setProductDescription(updatedAuction.getProductDescription());
                    }
                    if (updatedAuction.isFinished() == false) {
                        existingAuction.setFinished(updatedAuction.isFinished());
                    }
                        return auctionRepository.save(existingAuction);
                }).orElseThrow(() -> new RuntimeException("Auction not found!"));
    }

    // DELETE 1 by id
    public String deleteAuction(String id) {
        auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("Auction does not exists!"));
        auctionRepository.deleteById(id);
        return "Auction deleted!";
    }
    // Delete all to drop clean collection remotely (only for testing don't keep to production)
    public void deleteAllAuctions(){
        auctionRepository.deleteAll();
    }
}
