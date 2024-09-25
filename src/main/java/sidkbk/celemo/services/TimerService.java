package sidkbk.celemo.services;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sidkbk.celemo.Mail.EmailHandler;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.helper.LoggedInUsers;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimerService {

    private final AuctionRepository auctionRepository;
    private final OrderService orderService;

    private final UserRepository userRepository;
    private final EmailHandler emailHandler;

    private final SimpMessagingTemplate messagingTemplate;


    public TimerService(AuctionRepository auctionRepository, OrderService orderService, UserRepository userRepository, EmailHandler emailHandler, SimpMessagingTemplate messagingTemplate) {
        this.auctionRepository = auctionRepository;
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.emailHandler = emailHandler;
        this.messagingTemplate = messagingTemplate;
    }


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
                if (auction.isFinished && auction.isHasBids()) {
                    OrderCreationDTO orderCreationDTO = new OrderCreationDTO();
                    orderCreationDTO.setAuctionId(auction.getId());
                    orderService.createOrder(orderCreationDTO);

                    // order has been created

                    // skicka notis till ägare av auktion


                    if (LoggedInUsers.userList.containsKey(auction.getSeller())) {
                        messagingTemplate.convertAndSendToUser(
                                //foundAuction.getSeller()
                                auction.getSeller(),
                                "/private",
                                "Your auction has ended. Title: " + auction.getTitle()
                        );
                    } else {
                        Optional<User> seller = userRepository.findById(auction.getSeller());
                        // skicka mail
                        System.out.println("mail skickat till: " + seller.get().getEmail());
                        emailHandler.sendMail(seller.get().getEmail(), auction.getTitle(), auction.getEndPrice());
                    }
                } else {
                    System.out.println("Auction has no bids, order not created.");
                }


            }


        }

    }


}


