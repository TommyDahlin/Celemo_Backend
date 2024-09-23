package sidkbk.celemo.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TestService {

    @Scheduled(fixedRate = 5000)
    public void timerOne() throws InterruptedException {
        int x = 20;
        for (int i = 0; i < x; i++) {
            System.out.println("Timer 1: " + i + " seconds");
            TimeUnit.SECONDS.sleep(1);
        }
    }
    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void timerTwo() throws InterruptedException {
        int x = 20;
        for (int i = 0; i < x; i++) {
            System.out.println("Timer 2: " + i + " seconds");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
