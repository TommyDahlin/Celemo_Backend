package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.OrderRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

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





    // Create an order
    public Order createOrder(Order order) {
        Auction findAuction = auctionRepository.findById(order.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Couldn't find Auction"));
        // Finding seller from account repository
        User findSellerAccount = userRepository.findById(findAuction.getSellerId())
                .orElseThrow(() -> new RuntimeException("Couldn't find seller."));
            order.setSellerAccount(findSellerAccount);
        // Finding buyer from account repository
        // NEEDS BIDS TO BE FINISHED TO PROCEED WITH BUYERACCOUNTID
        User findBuyerAccount = userRepository.findById(order.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Couldn't find buyer."));
            order.setBuyerAccount(findBuyerAccount);
            order.setAuction(findAuction);

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

    // Delete one order by id
    public String deleteOrder(String id) {
        orderRepository.deleteById(id);
        return "Deleted order successfully!";
    }

}
