package sidkbk.celemo.services;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimerService {

    public static void checkAuctionEndTimeTimer() {
        ScheduledExecutorService auctionTimer = new ScheduledThreadPoolExecutor(2);
        auctionTimer.scheduleWithFixedDelay(AuctionService::checkAuctionEndTime,10 , 10, TimeUnit.SECONDS);
    }
}
