package sidkbk.celemo.dto.Reviews;

import jakarta.validation.constraints.NotBlank;

public class ReviewsPutDTO {

    @NotBlank
    private String reviewId;

    private Double grade;

    private String reviewText;

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
}
