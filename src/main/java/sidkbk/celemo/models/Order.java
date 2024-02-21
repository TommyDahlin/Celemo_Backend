package sidkbk.celemo.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    @DBRef
    private User sellerAccount;
    @DBRef
    private User buyerAccount;
    @DBRef
    private Auction auction;

    private String auctionId;

    private String sellerId;

    private String buyerId;

    //@DBRef
    private String productTitle;
    //@DBRef
    private int endPrice;
    //@DBRef
    private String endDate;

    //calculating total ammount for the commission of 3%
    //private double commission = endPrice * 0.03;


    public Order() {
    }


    public String getId() {
        return id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public int getEndPrice() {
        return endPrice;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public User getSellerAccount() {
        return sellerAccount;
    }

    public void setSellerAccount(User sellerAccount) {
        this.sellerAccount = sellerAccount;
    }

    public User getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(User buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

  /*  public double getCommission() {
        return commission;
    }*/
}
