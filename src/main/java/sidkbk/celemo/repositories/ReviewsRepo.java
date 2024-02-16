package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sidkbk.celemo.models.Reviews;

@Repository
public interface ReviewsRepo extends MongoRepository<Reviews, String> {

}
