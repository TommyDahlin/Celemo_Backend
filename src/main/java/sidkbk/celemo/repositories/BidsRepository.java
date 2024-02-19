package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Bids;

public interface BidsRepository extends MongoRepository<Bids, String> {
}
