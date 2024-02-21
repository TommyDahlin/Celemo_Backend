package sidkbk.celemo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "auctions")
public class Auction {
    // Everything from here to the comment of bids are required before we can post an auction to the database
    // sellerId is the user that makes the auction, the rest is self-explanatory.
    @Id
    public String id;
    @NotBlank
    public String sellerId;
    public String bidId;
    @DBRef
    private Bids bid;
    @DBRef
    private User user;
    @NotBlank
    public String title;
    @NotBlank
    public String productDescription;
    public String productPhoto;
    public String celebrityName;
    @NotNull
    public double startPrice;

    // Not needed for the body in postman, gets added automatically
    public LocalDate startingDate = LocalDate.now();
    public LocalDate endDate = LocalDate.now().plusDays(7);

    // Bids
    public double currentPrice;
    public double endPrice;

    // Both booleans have to be true to move on to make an order.
    public boolean isFinished;
    public boolean hasBids;

    // Enum List
    public List<ECategory> categoryList = new ArrayList<>();

    public Auction(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCelebrityName() {
        return celebrityName;
    }

    public void setCelebrityName(String celebrityName) {
        this.celebrityName = celebrityName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    public boolean isHasBids() {
        return hasBids;
    }

    public void setHasBids(boolean hasBids) {
        this.hasBids = hasBids;
    }

    public List<ECategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ECategory> categoryList) {
        this.categoryList = categoryList;
    }
}
