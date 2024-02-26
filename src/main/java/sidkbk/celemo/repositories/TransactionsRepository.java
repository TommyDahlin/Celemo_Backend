package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sidkbk.celemo.models.Transactions;

@Repository
public interface TransactionsRepository extends MongoRepository<Transactions, String> {
}
