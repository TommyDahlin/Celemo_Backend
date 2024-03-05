package sidkbk.celemo.dto.Reviews;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewsSortLowHighDTO {

    // Variables
    @NotBlank
    private String reviewedUserId;
    @NotNull
    private String lowOrHigh; // LOW or HIGH

    private int pageSize = 3; // OPTIONAL Used with paging

    // Getters
    public String getReviewedUserId() {
        return reviewedUserId;
    }
    public String getLowOrHigh() {
        if (this.lowOrHigh.toUpperCase().equals("LOW")) {
            return "LOW";
        } else {
            return "HIGH";
        }
    }
    public int getPageSize() {
        return pageSize;
    }

    // Setters
    public void setReviewedUserId(String reviewedUserId) {
        this.reviewedUserId = reviewedUserId;
    }
    public void setLowOrHigh(String lowOrHigh) {
        this.lowOrHigh = lowOrHigh;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
