package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sidkbk.celemo.repositories.CelemoObject;

import java.time.LocalDate;


@Document(collection = "bids")
public class Bids implements CelemoObject {


    @Id
    private String id;

    private String user; // this could be a problem


    private String auctionId; // This i a String because otherwise we get infinte recursion error


    private double startPrice;
    private double currentPrice;
    private double maxPrice;

    public Bids() {
    }

    public Bids(String user, String auctionId, double startPrice, double maxPrice) {
    }


    public String getId() {
        return id;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }


    public double getStartPrice() {
        return startPrice;
    }
    public double getCurrentPrice() {
        return currentPrice;
    }
    public double getMaxPrice() {
        return maxPrice;
    }


    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @CreatedDate
    public LocalDate timestamp = LocalDate.now();





}
