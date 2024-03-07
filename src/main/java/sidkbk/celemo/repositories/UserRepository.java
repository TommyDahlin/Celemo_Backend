package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.User;


public interface UserRepository extends MongoRepository<User, String> {


}
