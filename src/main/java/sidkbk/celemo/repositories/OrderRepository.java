package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByBuyerAccount_Id(String userId);


}
