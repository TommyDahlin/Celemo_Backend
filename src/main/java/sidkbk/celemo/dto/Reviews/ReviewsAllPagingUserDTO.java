package sidkbk.celemo.dto.Reviews;

import jakarta.validation.constraints.NotBlank;

public class ReviewsAllPagingUserDTO {

    // Variables
    @NotBlank
    private String reviewedUserId;
    private int pageSize = 3; // A default of 10 is better, for now 3 is better for testing when db is low


    // Getters & Setters
    public String getReviewedUserId() {
        return reviewedUserId;
    }
    public void setUserId(String reviewedUserId) {
        this.reviewedUserId = reviewedUserId;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
