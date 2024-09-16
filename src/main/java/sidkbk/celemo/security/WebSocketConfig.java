package sidkbk.celemo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {
    // config för websocket
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableSimpleBroker("/topic", "/user");
        // aktiverar en meddelandebroker som skickar meddelanden till klienten
        // används för att skicka meddelanden till flera prenumeranter (broadcast),
        // medan /user skickar meddelanden till en specifik användare.
        config.setApplicationDestinationPrefixes("/app");
        // prefix för messages som skickas från client
        config.setUserDestinationPrefix("/user");
        // viktigt för user-specific messaging
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:5173").withSockJS();
        // skapar en websocket-endpoint på /ws, tillåter anslutningar från den angivna
        // ursprungsadressen, och aktiverar SockJS som fallback om websocket inte stöds av klienten.
    }
}
