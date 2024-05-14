package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String productTitle;
    private double endPrice;
    @CreatedDate
    private Date createdAt = new Date();


    private String sellerUsername;

    private String buyerUsername;
    @DBRef
    private Auction auction; // this could be a problem








    public Order() {
    }



    public String getId() {
        return id;
    }
    public String getSellerUsername() {
        return sellerUsername;
    }
    public String getBuyerUsername() {
        return buyerUsername;
    }
    public Auction getAuction() {
        return auction;
    }
    public String getProductTitle() {
        return productTitle;
    }
    public double getEndPrice() {
        return endPrice;
    }
    public Date getCreatedAt() {
        return createdAt;
    }





    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }
    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }
    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public void setId(String id) {
        this.id = id;
    }
}
