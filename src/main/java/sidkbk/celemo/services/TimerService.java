package sidkbk.celemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Bids;
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

    private final SimpMessagingTemplate messagingTemplate;


    public TimerService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        AuctionRepository auctionRepository,
                        BidsRepository bidsRepository,
                        UserService userService,
                        SimpMessagingTemplate messagingTemplate) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.bidsRepository = bidsRepository;
        this.userService = userService;
        this.orderService = new OrderService(orderRepository, userRepository, auctionRepository, bidsRepository, userService);
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    NotificationService notificationService;


    OrderService orderService;

    // Scheduled method to check if an auctions end time has passed, then set the auction to finished.
    @Scheduled(fixedDelay = 20 * 1000, initialDelay = 20 * 1000)
    public void checkAuctionEndTime() {
        List<Auction> allAuctions = auctionRepository.findAll();
        for (Auction auction : allAuctions) {
            // If current time has passed end time
            if (auction.getEndDate().isBefore(LocalDateTime.now()) && !auction.isFinished) {
                auction.setEndPrice(auction.getCurrentPrice());
                auction.setFinished(true);
                auctionRepository.save(auction);

                // your auction has ended (to owner)
                // skicka notis till ägare av auktion

                messagingTemplate.convertAndSendToUser(
                        //foundAuction.getSeller()
                        auction.getSeller(),
                        "/private",
                        "Your auction has ended. Title: " + auction.getTitle()
                );
                Bids nBid = bidsRepository.findById(auction.getBid()).get();
                // skicka notis till ägare av auktion
                // har inte med det i clienten utan bara testat på user (bidder)
                messagingTemplate.convertAndSendToUser(
                        //foundAuction.getSeller()
                        nBid.getUser(),
                        "/private",
                        "Your won auction: " + auction.getTitle()
                );

                //notificationService.createNotifUser(auction.getSeller(), "Your auction has ended. Title: " + auction.getTitle());


                System.out.print("Auction: " + auction.getId() + " set to finished. ");

                // If auction is finished AND has bids THEN create an Order.
                if (auction.isFinished && auction.isHasBids()) {
                    OrderCreationDTO orderCreationDTO = new OrderCreationDTO();
                    orderCreationDTO.setAuctionId(auction.getId());
                    orderService.createOrder(orderCreationDTO);

                    // order has been created

                    // your auction has ended (to owner)
                    // skicka notis till ägare av auktion
                    // har inte med det i clienten utan bara testat på user (bidder)
                    messagingTemplate.convertAndSendToUser(
                            //foundAuction.getSeller()
                            auction.getSeller(),
                            "/private",
                            "Your auction has ended and order has been created. Title: " + auction.getTitle()
                    );

                    //notificationService.createNotifUser(auction.getSeller(), "Your auction has ended and order has been created. Title: " + auction.getTitle());


                } else {
                    System.out.println("Auction has no bids, order not created.");
                }
            }
        }
    }
}
