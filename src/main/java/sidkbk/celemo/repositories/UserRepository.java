package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sidkbk.celemo.models.User;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    //find all users and return username and email
    List<User> findAllById(String id);
}
