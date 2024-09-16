package sidkbk.celemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.repositories.AuctionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Service
public class TimerService {

    @Autowired
    static AuctionRepository auctionRepository;

    public static void checkAuctionEndTimeTimer() {
        ScheduledExecutorService auctionTimer = new ScheduledThreadPoolExecutor(2);
        auctionTimer.scheduleWithFixedDelay(TimerService::checkAuctionEndTime,10 , 10, TimeUnit.SECONDS);
    }

    public static void checkAuctionEndTime() {
        List<Auction> allAuctions = auctionRepository.findAll();
        for (Auction auction : allAuctions) {
            if (auction.getEndDate().isBefore(LocalDateTime.now())) {
                auction.setFinished(true);
                System.out.println("an acution set to finished");
            }
        }
    }
}
