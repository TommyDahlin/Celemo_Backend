package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
public class Reports {

    public String getId() {
        return id;
    }


    @Id
    private String id;


    @CreatedDate
    private String createdAt;


    private String content;

    public Reports() {
    }

    public String getContent() {
        return content;
    }

    // User reference, find a user by id,

    @DBRef
    private Account account;

    @DBRef
    private Auction auction;

    private String reoprtingUserId;

    private String reportedUserId;

    private String auctionId;



    public String getCreatedAt() {
        return createdAt;
    }

    public Account getAccount() {
        return account;
    }

    public Auction getAuction() {
        return auction;
    }

    public String getReoprtingUserId() {
        return reoprtingUserId;
    }

    public String getReportedUserId() {
        return reportedUserId;
    }

    public String getAuctionId() {
        return auctionId;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public void setReoprtingUserId(String reoprtingUserId) {
        this.reoprtingUserId = reoprtingUserId;
    }

    public void setReportedUserId(String reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }








}
