package sidkbk.celemo.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

public class ReportsUserDTO {



    @CreatedDate
    private Date createdAt = new Date();
    @NotBlank
    private String content;

    @NotBlank
    private String reportingUserId;

    @NotBlank
    private String reportedUserId;





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

    public String getReportingUserId() {
        return reportingUserId;
    }

    public void setReportingUserId(String reportingUserId) {
        this.reportingUserId = reportingUserId;
    }

    public String getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(String reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

}
