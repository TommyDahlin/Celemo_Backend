package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.order.DeleteOrderDTO;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.dto.order.OrderFoundByIdDTO;
import sidkbk.celemo.dto.order.PreviousPurchaseFromOrderDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.OrderRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Order> findPreviousPurchase(PreviousPurchaseFromOrderDTO previousPurchaseFromOrderDTO) {
        userRepository.findById(previousPurchaseFromOrderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("UserId could not be found"));
            List<Order> previousPurchase = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            if (order.getBuyerAccount() != null && previousPurchaseFromOrderDTO.getUserId().equals(order.getBuyerAccount().getId())) {
                previousPurchase.add(order);
              }
        }
        return previousPurchase;
    }
  
    //HELENA:
    // vi hittar EN order men hittar vi EN order som tillhör en specifik user?
    // hittar vi ALLA ordrar som tillhör en specifik user?

   
    //HELENA:
    // en order kan man INTE uppdatera då blir revisorn arg...
    // man makulerar och skapar en ny i så fall

            

    // Delete one order by orderId
    public ResponseEntity<?> deleteOrder(DeleteOrderDTO deleteOrderDTO) {
        //tries to find the orderId , if found it gets deleted
        orderRepository.findById(deleteOrderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order does not exist"));

        orderRepository.deleteById(deleteOrderDTO.getOrderId());
        return ResponseEntity.status(HttpStatus.OK).body("Order was deleted successfully!");
    }
}
