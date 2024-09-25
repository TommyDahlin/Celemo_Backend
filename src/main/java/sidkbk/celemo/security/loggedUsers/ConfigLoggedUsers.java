package sidkbk.celemo.security.loggedUsers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigLoggedUsers {
    @Bean
    public ActiveUserStore activeUserStore(){
        return new ActiveUserStore();
    }
}
