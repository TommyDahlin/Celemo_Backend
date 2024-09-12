package sidkbk.celemo.models;

import lombok.Builder;
import lombok.Getter;
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

    private String buyerID;

    private String buyerFullName;

    private Date createdDate;

    private String productTitle;

    private Double endPrice;       //endPrice * 0.97

    private Double commission;   // endPrice * 0.03
}
