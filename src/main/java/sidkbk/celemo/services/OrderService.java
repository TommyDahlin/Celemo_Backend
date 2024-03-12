package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.order.DeleteOrderDTO;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.dto.order.OrderFoundByIdDTO;
import sidkbk.celemo.dto.user.FindUserIdDTO;
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



    public Order createOrder(OrderCreationDTO orderCreationDTO) {
        Auction findAuction = auctionRepository.findById(orderCreationDTO.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction not found!"));
        User findSellerId = userRepository.findById(findAuction.getSeller().getId())
                .orElseThrow(() -> new RuntimeException("SellerId was not found"));
        User findBuyerId = userRepository.findById(orderCreationDTO.getBuyerId())
                .orElseThrow(() -> new RuntimeException("BuyerId was not found"));

        Order newOrder = new Order();
        newOrder.setAuction(findAuction);
        newOrder.setSellerAccount(findSellerId);
        newOrder.setBuyerAccount(findBuyerId);
        newOrder.setProductTitle(findAuction.getTitle());
        newOrder.setEndPrice(findAuction.getEndPrice());
        newOrder.setCreatedAt(orderCreationDTO.getCreatedAt());

        // Run method to remove finished acution from users favourite lists.
        userService.removeFavouriteAuctionFromUsers(findAuction.getId());

        findAuction.setFinished(true);
        auctionRepository.save(findAuction);

        return orderRepository.save(newOrder);
    }

    // READ ALL ORDERS
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    // Find one specific order by orderId
    public Optional<Order> getOneOrder(OrderFoundByIdDTO orderFoundByIdDTO) {
        return orderRepository.findById(orderFoundByIdDTO.getOrderId());
    }

    // fine all orders that are bound to one user ID
    public List<Map<String, Object>> findOrdersByUserId(FindUserIdDTO findUserIdDTO) {
        //Tries to find orders by userId
        List<Order> findOrders = orderRepository.findByBuyerAccount_Id(findUserIdDTO.getUserId());
        // returns the orders it finds that are connected to the userId
        //then it maps thrue the orders and shows only whats inside the .map
        // Tho it dosnt seem to show in the order i put the orderDetails in.
        if(findOrders.isEmpty()){
            throw new RuntimeException("no orders exists or incorrect id given");
        } else {
            return findOrders.stream()
                    .map(order -> {
                        Map<String, Object> orderDetails = new HashMap<>();
                        orderDetails.put("ProductTitle:", order.getProductTitle());
                        orderDetails.put("BuyerUsername", order.getBuyerAccount().getUsername());
                        orderDetails.put("SellerUsername", order.getSellerAccount().getUsername());
                        orderDetails.put("endPrice", order.getEndPrice());
                        orderDetails.put("createdAt", order.getCreatedAt());
                        return orderDetails;
                    })
                    //then it adds it to a list.
                    .collect(Collectors.toList());

        }
    }

    // Delete one order by orderId
    public ResponseEntity<?> deleteOrder(DeleteOrderDTO deleteOrderDTO) {
        //tries to find the orderId , if found it gets deleted
        orderRepository.findById(deleteOrderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order does not exist"));

        orderRepository.deleteById(deleteOrderDTO.getOrderId());
        return ResponseEntity.status(HttpStatus.OK).body("Order was deleted successfully!");
    }


    public List<Order> findAllOrderForOneUser(FindUserIdDTO findUserIdDTO) {
        //Find user using id
        userRepository.findById(findUserIdDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        //Skapa en tom lista för hitta order
        List<Order> foundOrder = new ArrayList<>();
        //spare all order i en lista
        List<Order> allOrder = orderRepository.findAll();
        for (Order order : allOrder) {
            if (order.getBuyerAccount() != null && order.getBuyerAccount().getId().equals(findUserIdDTO.getUserId())) {
                foundOrder.add(order);
            }
        }
        return allOrder;
    }


}
