package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.OrderRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuctionRepository auctionRepository;





    // Create an order
    public Order createOrder(Order order) {
        Auction findAuction = auctionRepository.findById(order.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Couldn't find Auction"));
        // Finding seller from account repository
        User findSellerAccount = userRepository.findById(findAuction.getSellerId())
                .orElseThrow(() -> new RuntimeException("Couldn't find seller."));
            order.setSellerAccount(findSellerAccount);
        // Finding buyer from account repository
        // NEEDS AUCTION TO BE FINISHED TO PROCEED WITH BUYERACCOUNTID
        User findBuyerAccount = userRepository.findById(order.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Couldn't find buyer."));
            order.setBuyerAccount(findBuyerAccount);
        return orderRepository.save(order);
    }

    // READ ALL ORDERS
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    //READ 1 ORDER
    public Order getOneOrder(String id) {
        Order foundOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order was not found"));
        return foundOrder;
    }

    // PUT update one order
    public Order updateOrder(String orderId, Order updateOrder) {
        return orderRepository.findById(orderId)
                .map(existingOrder -> {
                    if (updateOrder.getBuyerAccount() != null) {
                        existingOrder.setBuyerAccount(updateOrder.getBuyerAccount());
                    }
                    if (updateOrder.getSellerAccount() != null) {
                        existingOrder.setSellerAccount(updateOrder.getSellerAccount());
                    }
                    if (updateOrder.getAuction() != null) {
                        existingOrder.setAuction(updateOrder.getAuction());
                    }
                    return orderRepository.save(existingOrder);
                }).orElseThrow(() -> new RuntimeException("Order was not found"));
    }

    // Delete one order by id
    public String deleteOrder(String id) {
        orderRepository.deleteById(id);
        return "Deleted order successfully!";
    }

}
