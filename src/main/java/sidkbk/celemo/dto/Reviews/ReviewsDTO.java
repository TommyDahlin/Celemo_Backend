package sidkbk.celemo.dto.Reviews;

public class ReviewsDTO {


    private String reviewId;

    private Double grade;

    private String reviewText;
    private String createdById;
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
