package sidkbk.celemo.dto.Reviews;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewsGetByGradeDTO {

    // Variables
    @NotBlank
    private String reviewedUserId;
    @NotNull
    private double grade;

    private int pageSize = 3; // OPTIONAL Used with paging

    //Getters & Setters
    public String getReviewedUserId() {
        return reviewedUserId;
    }
    public void setUserId(String reviewedUserId) {
        this.reviewedUserId = reviewedUserId;
    }
    public double getGrade() {
        return grade;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
