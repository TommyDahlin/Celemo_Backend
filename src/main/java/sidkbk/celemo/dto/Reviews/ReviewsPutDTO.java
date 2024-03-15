package sidkbk.celemo.dto.Reviews;

import jakarta.validation.constraints.NotBlank;

public class ReviewsPutDTO {

    @NotBlank
    private String reviewId;

    private String reviewText;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
