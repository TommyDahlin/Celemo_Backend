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
    private Short grade; // 1-5

    @NotBlank(message = "Field cannot be blank!")
    private String reviewText;

    @CreatedDate
    private Date createdAt = new Date();

    @DBRef
    private User createdBy;

    @DBRef
    private User reviwedUser;

    // Constructors
    public Reviews() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public Short getGrade() {
        return grade;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public User getReviwedUser() {
        return reviwedUser;
    }

    // Setters
    public void setGrade(Short grade) {
        this.grade = grade;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setReviwedUser(User reviwedUser) {
        this.reviwedUser = reviwedUser;
    }
}
