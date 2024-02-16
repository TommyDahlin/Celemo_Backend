package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Reviews;

public interface ReviewsRepo extends MongoRepository<Reviews, String> {

}
