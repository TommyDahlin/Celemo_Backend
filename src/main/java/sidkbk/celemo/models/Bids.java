package sidkbk.celemo.models;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(collection = "bids")
public class Bids {


    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Auction auction;


    public Bids() {
    }


    public String getId() {
        return id;
    }


    public Auction getAuction() {
        return auction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    @CreatedDate
    public LocalDate timestamp = LocalDate.now();





}
