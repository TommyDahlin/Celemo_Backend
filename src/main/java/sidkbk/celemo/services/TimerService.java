package sidkbk.celemo.services;


import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.OrderRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TimerService {

    private final AuctionRepository auctionRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BidsRepository bidsRepository;
    private final UserService userService;

    public TimerService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        AuctionRepository auctionRepository,
                        BidsRepository bidsRepository,
                        UserService userService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.bidsRepository = bidsRepository;
        this.userService = userService;
        this.orderService = new OrderService(orderRepository, userRepository, auctionRepository, bidsRepository, userService);
    }

    OrderService orderService;

    // Scheduled method to check if an auctions end time has passed, then set the auction to finished.
    /*@Scheduled(fixedDelay = 20 * 1000, initialDelay = 20 * 1000)*/
    public void checkAuctionEndTime() {
        List<Auction> allAuctions = auctionRepository.findAll();
        for (Auction auction : allAuctions) {
            // If current time has passed end time
            if (auction.getEndDate().isBefore(LocalDateTime.now()) && !auction.isFinished) {
                auction.setEndPrice(auction.getCurrentPrice());
                auction.setFinished(true);
                auctionRepository.save(auction);
                System.out.print("Auction: " + auction.getId() + " set to finished. ");

                // If auction is finished AND has bids THEN create an Order.
                if(auction.isFinished && auction.isHasBids()) {
                    OrderCreationDTO orderCreationDTO = new OrderCreationDTO();
                    orderCreationDTO.setAuctionId(auction.getId());
                    orderService.createOrder(orderCreationDTO);
                } else {
                    System.out.println("Auction has no bids, order not created.");
                }
            }
        }
    }
}
