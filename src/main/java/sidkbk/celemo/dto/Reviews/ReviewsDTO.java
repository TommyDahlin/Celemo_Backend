package sidkbk.celemo.dto.Reviews;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewsDTO {

    @NotBlank(message = "reviewId cannot be blank")
    private String reviewId;
    @NotNull(message = "grade cannot be blank")
    private Double grade;

    @NotBlank(message = "reviewText cannot be blank")
    private String reviewText;
    @NotBlank(message = "createById cannot be blank")
    private String createdById;
    @NotBlank(message = "reviewedUserId cannot be blank")
    private String reviewedUserId;


    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getReviewedUserId() {
        return reviewedUserId;
    }

    public void setReviewedUserId(String reviewedUserId) {
        this.reviewedUserId = reviewedUserId;
    }
}
