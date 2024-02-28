package sidkbk.celemo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "reviews")
public class Reviews {

    // Variables
    @Id
    private String id;

    @NotNull(message = "Field cannot be blank!")
    private Double grade; // 1-5

    @NotBlank(message = "Field cannot be blank!")
    private String reviewText;

    @CreatedDate
    private Date createdAt = new Date();


    @DBRef
    private User createdBy;

    @DBRef
    private User reviewedUser;

    // Constructors
    public Reviews() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public Double getGrade() {
        return grade;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Date getCreatedAt(Date createdAt) {
        return this.createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public User getReviewedUser() {
        return reviewedUser;
    }

    // Setters
    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setReviewedUser(User reviewedUser) {
        this.reviewedUser = reviewedUser;
    }
}
