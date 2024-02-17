package sidkbk.celemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Bids;

public interface BidsRepository extends MongoRepository<Bids, String> {
}
