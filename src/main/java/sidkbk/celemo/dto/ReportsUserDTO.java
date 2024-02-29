package sidkbk.celemo.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.User;

import java.util.Date;

public class ReportsUserDTO {

    private String id;

    @CreatedDate
    private Date createdAt = new Date();
    @NotBlank
    private String content;

    @NotBlank
    @DBRef
    private User reportingUserId;

    @NotBlank
    @DBRef
    private User reportedUserId;



    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getReportingUserId() {
        return reportingUserId;
    }

    public void setReportingUserId(User reportingUserId) {
        this.reportingUserId = reportingUserId;
    }

    public User getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(User reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
