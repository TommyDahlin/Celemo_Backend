package sidkbk.celemo.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Auction;

import java.util.List;


public interface AuctionRepository extends MongoRepository<Auction, String> {

    List<Auction> findByTitleContainsIgnoreCase(String string, Pageable pageable);
    List<Auction> findByCategoryListContains(String string, Pageable pageable);
    // find all auctions from user
    List<Auction> findAuctionBySeller(String seller);



}
