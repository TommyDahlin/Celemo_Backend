package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.order.DeleteOrderDTO;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.OrderRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    UserService userService;


    // CREATE AN ORDER
    public Order createOrder(OrderCreationDTO orderCreationDTO) {
        Auction foundAuction = auctionRepository.findById(orderCreationDTO.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction not found!"));

        User seller = userRepository.findById(orderCreationDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User buyer = userRepository.findById(orderCreationDTO.getBuyerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order newOrder = new Order.OrderBuilder()
                .auctionId(orderCreationDTO.getAuctionId())
                .buyerId(orderCreationDTO.getBuyerId())
                .sellerId(orderCreationDTO.getSellerId())
                .sellerFullName(seller.getFirstName() + " " + seller.getLastName())
                .buyerFullName(buyer.getFirstName() + " " + buyer.getLastName())
                .productTitle(foundAuction.getTitle())
                .commission(foundAuction.getEndPrice() * 0.03)
                .endPrice(foundAuction.getEndPrice())
                .build();

        // Run method to remove finished acution from users favourite lists.
        userService.removeFavouriteAuctionFromUsers(foundAuction.getId());

        return orderRepository.save(newOrder);
    }

    // Find one specific order by orderId
    public Optional<Order> getOneOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    // Find all orders for one user
    public List<Order> findAllOrdersOneUser(String buyerId) {
        return orderRepository.findOrderByBuyerId(buyerId);
    }

    // Admin
    // Delete one order by orderId
    public ResponseEntity<?> deleteOrder(DeleteOrderDTO deleteOrderDTO) {
        //tries to find the orderId , if found it gets deleted
        orderRepository.findById(deleteOrderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order does not exist"));

        orderRepository.deleteById(deleteOrderDTO.getOrderId());
        return ResponseEntity.status(HttpStatus.OK).body("Order was deleted successfully!");
    }

    // Admin
    // READ ALL ORDERS
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }



}
