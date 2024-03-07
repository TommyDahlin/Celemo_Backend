package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.models.Reviews;

import java.util.List;

public interface BidsRepository extends MongoRepository<Bids, String> {
    List<Bids> findBidsByAuctionId(String auctionId);
    List<Bids> findAllByAuctionId(String auctionId);
}