package sidkbk.celemo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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

    // @DBRF
    // private String // i might need this for celeb name for findPreviousPurchase method
    private String auctionId;

    private String sellerId;

    private String buyerId;

    //@DBRef
    private String productTitle;
    //@DBRef
    private int endPrice;
    //@DBRef
    private String endDate;

    public String celebrityName;







    public Order(String id, String productTitle, String endDate, int endPrice, String celebrityName) {
        this.id = id;
        this.productTitle = productTitle;
        this.endDate = endDate;
        this.endPrice = endPrice;
        this.celebrityName = celebrityName;

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

  /*  public int getEndPrice(List<Order> previousPurchase) {
        return endPrice;
    }
*/
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

    public int getEndPrice() {
        return endPrice;
    }

    public String getCelebrityName() {
        return celebrityName;
    }

  /*  public double getCommission() {
        return commission;
    }*/
}
