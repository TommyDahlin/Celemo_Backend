package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "reports")
public class Reports {

    public String getId() {
        return id;
    }



    @Id
    private String id;


    public LocalDate getTimestamp() {
        return timestamp;
    }

    @CreatedDate
    public LocalDate timestamp = LocalDate.now();



    private String content;




    public Reports() {
    }


    @DBRef
    private User reportingUserId;


    @DBRef
    private User reportedUserId;

    @DBRef
    private Auction auction;




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






    // User reference, find a user by id,









}
