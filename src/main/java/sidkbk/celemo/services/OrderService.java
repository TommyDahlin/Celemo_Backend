package sidkbk.celemo.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.order.DeleteOrderDTO;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.helper.ObjectHelper;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.OrderRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final BidsRepository bidsRepository;
    private final UserService userService;
    private final ObjectHelper objectHelper;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        AuctionRepository auctionRepository,
                        BidsRepository bidsRepository,
                        UserService userService,
                        ObjectHelper objectHelper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.bidsRepository = bidsRepository;
        this.userService = userService;
        this.objectHelper = objectHelper;
    }

    // CREATE AN ORDER
    public Order createOrder(OrderCreationDTO orderCreationDTO) {

        Auction foundAuction = (Auction) objectHelper.findObject("auction", orderCreationDTO.getAuctionId());
        User seller = (User) objectHelper.findObject("user", foundAuction.getSeller());
        Bids winningBid = (Bids) objectHelper.findObject("bids", foundAuction.getBid());
        User buyer = (User) objectHelper.findObject("user", winningBid.getUser());

        // Builder pattern
        Order newOrder = new Order.OrderBuilder()
                .auctionId(orderCreationDTO.getAuctionId())
                .buyerId(buyer.getId())
                .sellerId(foundAuction.getSeller())
                .sellerFullName(seller.getFirstName() + " " + seller.getLastName())
                .buyerFullName(buyer.getFirstName() + " " + buyer.getLastName())
                .productTitle(foundAuction.getTitle())
                .commission(foundAuction.getEndPrice() * 0.03)
                .endPrice(foundAuction.getEndPrice())
                .build();

        // Run method to remove finished acution from users favourite lists.
        userService.removeFavouriteAuctionFromUsers(foundAuction.getId());

        orderRepository.save(newOrder);
        System.out.println("Order created: " + newOrder.getId());
        return newOrder;
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
