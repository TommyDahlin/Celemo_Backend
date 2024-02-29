package sidkbk.celemo.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

public class ReportsAuctionDTO {


    @CreatedDate
    private Date createdAt = new Date();
    @NotBlank
    private String content;
    @NotBlank
    private String reportingUserId;

    @NotBlank
    private String auctionId;




    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReportingUserId() {
        return reportingUserId;
    }

    public void setReportingUserId(String reportingUserId) {
        this.reportingUserId = reportingUserId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }
}
