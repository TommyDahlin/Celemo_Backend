package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sidkbk.celemo.models.Auction;

import java.util.List;

@Repository
public interface AuctionRepository extends MongoRepository<Auction, String> {

    // find all auctions from user
    List<Auction> findAuctionBySeller(String seller);
}
