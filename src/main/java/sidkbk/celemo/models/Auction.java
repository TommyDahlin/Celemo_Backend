package sidkbk.celemo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document("collection = auctions")
public class Auction {
    @Id
    public String id;
    @DBRef
    public String sellerId;
    public String title;
    public String productDescription;
    public String productPhoto;
    public String celebrityName;

    // Bids
    public double startPrice;
    public double currentPrice;
    public double endPrice;
    // Booleans to check if the auction is able to finish and if the time has gone out.
    public boolean isFinished;
    public boolean hasBids;

    // Enum List
    //public List<Category> categoryList = new ArrayList<>();
}
