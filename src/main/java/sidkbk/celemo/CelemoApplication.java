package sidkbk.celemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class CelemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CelemoApplication.class, args);
		/*TimerService.checkAuctionEndTimeTimer();*/
	}

}
