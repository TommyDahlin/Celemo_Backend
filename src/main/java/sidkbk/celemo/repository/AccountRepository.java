package sidkbk.celemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Account;

public interface AccountRepository extends MongoRepository<Account, String> {
}
