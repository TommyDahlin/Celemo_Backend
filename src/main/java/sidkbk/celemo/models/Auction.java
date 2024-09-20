package sidkbk.celemo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "auctions")
public class Auction {
    // Everything from here to the comment of bids are required before we can post an auction to the database
    // sellerId is the user that makes the auction, the rest is self-explanatory.
    // jag vet inte om ni någon gång mappar om Enum till strängar men säger det här i alla fall att det bör ni göra
    // det blir MYCKET lättare i så fall och ni kan enkelt lösa pagination med filtrering om ni skulle vilja det
    @Id
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String productDescription;
    private String productPhoto;
    private String celebrityName;
    @NotNull
    private Double startPrice;

    // Not needed for the body in postman, gets added automatically
    private LocalDateTime startingDate = LocalDateTime.now();
    private LocalDateTime endDate;
    private int counter = 0;
    public Double currentPrice;
    private Double endPrice = 0d;
    private String bid; // this could be a problem

    // Both booleans have to be true to move on to make an order.
    public boolean isFinished;
    private boolean hasBids;

    // Enum List
    public List<ECategory> categoryList = new ArrayList<>();

    @NotBlank
    private String seller; // this could be a problem

    // Constructor =====================================================
    public Auction(){
    }

    public Auction(AuctionBuilder builder) {
        this.title = builder.title;
        this.productDescription = builder.productDescription;
        this.productPhoto = builder.productPhoto;
        this.celebrityName = builder.celebrityName;
        this.startPrice = builder.startPrice;
        this.currentPrice = builder.currentPrice;
        this.endDate = builder.endDate;
        this.categoryList = builder.categoryList;
        this.seller = builder.sellerId;
    }

    // Getters & Setters ===============================================
    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setEndPrice(Double endPrice) {
        this.endPrice = endPrice;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public String getSeller() {
        return seller;
    }

    public void setSellerId(String seller) {
        this.seller = seller;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }

    public static class AuctionBuilder {
        // Variables
        private String title;
        private String productDescription;
        private String productPhoto;
        private String celebrityName;
        private Double startPrice;
        private Double currentPrice;
        private LocalDateTime endDate;
        private List<ECategory> categoryList;
        private String sellerId;

        // Setters
        public AuctionBuilder setTitle(String title) {
            this.title = title;
            return this;
        }
        public AuctionBuilder setProductDescription(String productDescription) {
            this.productDescription = productDescription;
            return this;
        }
        public AuctionBuilder setProductPhoto(String productPhoto) {
            this.productPhoto = productPhoto;
            return this;
        }
        public AuctionBuilder setCelebrityName(String celebrityName) {
            this.celebrityName = celebrityName;
            return this;
        }
        public AuctionBuilder setStartPrice(Double startPrice) {
            this.startPrice = startPrice;
            return this;
        }
        public AuctionBuilder setCurrentPrice(Double currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }
        public AuctionBuilder setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }
        public AuctionBuilder setCategoryList(List<ECategory> categoryList) {
            this.categoryList = categoryList;
            return this;
        }
        public AuctionBuilder setSellerId(String sellerId) {
            this.sellerId = sellerId;
            return this;
        }
        public Auction build() {
            return new Auction(this);
        }
    }
}
