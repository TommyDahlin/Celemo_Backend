package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "reports")
public class Reports {



    @Id
    private String id;




    @CreatedDate
    public Date createdAt = new Date();

    private String content;



    @DBRef
    private User reportingUserId;

    @DBRef
    private User reportedUserId;

    @DBRef
    private Auction auction;

    public Reports() {
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getReportingUserId() {
        return reportingUserId;
    }

    public User getReportedUserId() {
        return reportedUserId;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setReportingUserId(User reportingUserId) {
        this.reportingUserId = reportingUserId;
    }

    public void setReportedUserId(User reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }



}
