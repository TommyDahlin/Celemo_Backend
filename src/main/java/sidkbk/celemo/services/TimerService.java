package sidkbk.celemo.services;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.repositories.AuctionRepository;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TimerService {

    private final AuctionRepository auctionRepository;

    public TimerService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 5 * 1000)
    public void checkAuctionEndTime() {
        List<Auction> allAuctions = auctionRepository.findAll();
        for (Auction auction : allAuctions) {
            if (auction.getEndDate().isBefore(LocalDateTime.now()) && !auction.isFinished) {
                auction.setEndPrice(auction.getCurrentPrice());
                auction.setFinished(true);
                auctionRepository.save(auction);
                System.out.println(auction.getId() + " set to finished");
            }
        }
    }
}
