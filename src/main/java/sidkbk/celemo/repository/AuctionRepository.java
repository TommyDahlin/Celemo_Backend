package sidkbk.celemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Auction;

public interface AuctionRepository extends MongoRepository<Auction, String> {
}
