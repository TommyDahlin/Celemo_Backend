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


    private String sellerAccount;

    private String buyerAccount;
    @DBRef
    private Auction auction;








    public Order() {
    }



    public String getId() {
        return id;
    }
    public String getSellerAccount() {
        return sellerAccount;
    }
    public String getBuyerAccount() {
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





    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }
    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }
    public void setSellerAccount(String sellerAccount) {
        this.sellerAccount = sellerAccount;
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
