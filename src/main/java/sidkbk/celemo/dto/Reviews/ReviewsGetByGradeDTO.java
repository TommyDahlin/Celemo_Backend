package sidkbk.celemo.dto.Reviews;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewsGetByGradeDTO {

    // Variables
    @NotBlank
    private String userId;
    @NotNull
    private double grade;

    private int pageSize = 3; // OPTIONAL Used with paging

    //Getters & Setters
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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
