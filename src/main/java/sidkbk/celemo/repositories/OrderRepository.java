package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Order;

public interface OrderRepository extends MongoRepository<Order, String> {


}
