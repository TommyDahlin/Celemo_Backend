package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.User;

public interface AccountRepository extends MongoRepository<User, String> {
}
