package sidkbk.celemo.models;

import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String productTitle;
    private double endPrice;
    private String test;
    @CreatedDate
    private Date createdAt = new Date();
    @DBRef
    private User sellerAccount;
    @DBRef
    private User buyerAccount;
    @DBRef
    private Auction auction;







    public Order() {
    }



    public String getId() {
        return id;
    }
    public User getSellerAccount() {
        return sellerAccount;
    }
    public User getBuyerAccount() {
        return buyerAccount;
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
    public String getTest() {
        return test;
    }




    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }
    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    public void setBuyerAccount(User buyerAccount) {
        this.buyerAccount = buyerAccount;
    }
    public void setSellerAccount(User sellerAccount) {
        this.sellerAccount = sellerAccount;
    }
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public void setTest(String test) {
        this.test = test;
    }
}
