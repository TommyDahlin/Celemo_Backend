package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.dto.order.OrderFoundByIdDTO;
import sidkbk.celemo.dto.order.PreviousPurchaseFromOrderDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.OrderRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.time.LocalDate;
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

        User findSellerId = userRepository.findById(orderCreationDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("SellerId never not found!"));

        User findBuyerId = userRepository.findById(orderCreationDTO.getBuyerId())
                .orElseThrow(() -> new RuntimeException("BuyerId was not found"));
//        double orderAmount = orderCreationDTO.getCommission();
//        double commissionRate = 0.03;
//        double commission = orderAmount * commissionRate;
        Order newOrder = new Order();

        newOrder.setAuction(findAuction);
        newOrder.setSellerAccount(findSellerId);
        newOrder.setBuyerAccount(findBuyerId);
        newOrder.setProductTitle(findAuction.getTitle());
        newOrder.setEndPrice(findAuction.getEndPrice());
        newOrder.setCreatedAt(orderCreationDTO.getCreatedAt());
//    newOrder.setTest(orderCreationDTO.getTest());
//    newOrder.setBids(orderCreationDTO.getCommission());
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



// hitta alla orders
// hitta buyerId
// jämför alla orderId med buyerId och se om dom matchar.
// om dom matchar adda dom till ny array list
// visa den nya array listan.

    /*
    public List<Order> findPreviousPurchase(String id) {
        List<Order> allOrders = orderRepository.findAll(); //list of all orders
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User was not found with: " + id));// Retrieves the user by its id,// instead of .get() i cast a EntityNotFoundException to make sure the user exists and to understand what was giving error 404
        List<Order> previousPurchase = new ArrayList<>();

        for (Order order : allOrders) { // takes a check on each order so see if it matches with buyerID
            if (order.getBuyerId() != null && order.getBuyerId().equals(id)) {

                Auction auction = order.getAuction(); //to access auction from order
                if (auction.getCelebrityName() != null) {// when celebrityname is null in database it cast message auction null, have it outcommented due to celbrity name being null in database for the orders we have so far.
                    Order orderHistory = new Order(order.getId(), order.getProductTitle(), order.getEndDate(), order.getEndPrice(), auction.getCelebrityName());

                    previousPurchase.add(order);
                }
            }
        }
            return previousPurchase;
    }
 */
//            if (order.getBuyerAccount() != null && order.getBuyerAccount().getId().equals(previousPurchaseFromOrderDTO.getBuyerId())) {

//                Order newOrder = new Order();
//                newOrder.setAuction(order.getAuction());
//                newOrder.setSellerAccount(order.getSellerAccount());
//                newOrder.setBuyerAccount(findUser);
//                newOrder.setProductTitle(order.getProductTitle());
//                newOrder.setEndPrice(order.getEndPrice());
//                previousPurchase.add(newOrder);

//            if (previousPurchaseFromOrderDTO.getUserId().equals(order.getBuyerAccount().getId())) {
//             previousPurchase.add(order);
//            }







