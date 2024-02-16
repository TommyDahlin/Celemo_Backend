package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;


    // Create an order
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // READ ALL ORDERS
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    //READ 1 ORDER
    public Order getOneOrder(String id) {
        return orderRepository.findById(id).get();
    }

    // PUT update one order
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    // Delete one order by id
    public String deleteOrder(String id) {
        orderRepository.deleteById(id);
        return "Deleted order successfully!";
    }

}
