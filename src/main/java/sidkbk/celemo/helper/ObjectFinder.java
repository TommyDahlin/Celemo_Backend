package sidkbk.celemo.helper;

import org.springframework.stereotype.Service;
import sidkbk.celemo.models.*;
import sidkbk.celemo.repositories.*;

@Service
public class ObjectFinder {

    private final AuctionRepository auctionRepository;
    private final BidsRepository bidsRepository;
    private final OrderRepository orderRepository;
    private final ReportsRepository reportsRepository;
    private final ReviewsRepo reviewsRepository;
    private final UserRepository userRepository;

    public ObjectFinder(AuctionRepository auctionRepository,
                        BidsRepository bidsRepository,
                        OrderRepository orderRepository,
                        ReportsRepository reportsRepository,
                        ReviewsRepo reviewsRepository,
                        UserRepository userRepository) {
        this.auctionRepository = auctionRepository;
        this.bidsRepository = bidsRepository;
        this.orderRepository = orderRepository;
        this.reportsRepository = reportsRepository;
        this.reviewsRepository = reviewsRepository;
        this.userRepository = userRepository;
    }

    // Method to find an object ex an auction or a bid.

    /* Example
    Instead of:
       User findUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find user."));
    You can:
       User findUser = objectHelper.findUserById(id);
    */

    public Auction findAuctionById(String id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
    }

    public Bids findBidById(String id) {
        return bidsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bid not found"));
    }

    public Order findOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Reports findReportById(String id) {
        return reportsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public Reviews findReviewById(String id) {
        return reviewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
