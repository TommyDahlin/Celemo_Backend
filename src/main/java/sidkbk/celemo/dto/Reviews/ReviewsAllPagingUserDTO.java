package sidkbk.celemo.dto.Reviews;

import jakarta.validation.constraints.NotBlank;

public class ReviewsAllPagingUserDTO {

    // Variables
    @NotBlank
    private String userId;
    private int pageSize = 3; // A default of 10 is better, for now 3 is better for testing now when db is low


    // Getters & Setters
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
