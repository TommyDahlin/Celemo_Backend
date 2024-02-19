package sidkbk.celemo.repositorys;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Account;

public interface AccountRepository extends MongoRepository<Account, String> {
}
