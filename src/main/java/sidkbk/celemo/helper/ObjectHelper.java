package sidkbk.celemo.helper;

import org.springframework.stereotype.Service;
import sidkbk.celemo.repositories.*;

@Service
public class ObjectHelper {

    private final AuctionRepository auctionRepository;
    private final BidsRepository bidsRepository;
    private final OrderRepository orderRepository;
    private final ReportsRepository reportsRepository;
    private final ReviewsRepo reviewsRepository;
    private final UserRepository userRepository;

    public ObjectHelper(AuctionRepository auctionRepository, BidsRepository bidsRepository, OrderRepository orderRepository, ReportsRepository reportsRepository, ReviewsRepo reviewsRepository, UserRepository userRepository) {
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
       User findUser = (User) objectHelper.findObject("user", id);
    */
    public CelemoObject findObject(String whatToFind, String id) {
        String caseName = whatToFind.toLowerCase();
        switch (caseName) {
            case "auction":
                return auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("Auction not found"));
            case "bids":
                return bidsRepository.findById(id).orElseThrow(() -> new RuntimeException("Bid not found"));
            case "order":
                return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
            case "reports":
                return reportsRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
            case "reviews":
                return reviewsRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
            case "user":
                return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            default:
                return null;
        }
    }
}
