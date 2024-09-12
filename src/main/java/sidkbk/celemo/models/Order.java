package sidkbk.celemo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Getter
@Builder
@Document(collection = "orders")
public class Order {

    // Variables
    @Id
    private String id;

    private String auctionId;

    private String sellerId;

    private String sellerFullName;

    private String buyerId;

    private String buyerFullName;

    private String productTitle;

    private Double endPrice;

    private Double commission;   // endPrice * 0.03

    @CreatedDate
    private Date createdDate;

    public static class OrderBuilder {
        public OrderBuilder () {}
    }
}
