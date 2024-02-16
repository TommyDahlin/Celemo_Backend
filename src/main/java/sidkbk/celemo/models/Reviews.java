package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "reviews")
public class Reviews {

    @Id
    private String id;

    private EGrade grade;

    @CreatedDate
    private Date createdAt = new Date();

    @DBRef
    private Account createdBy;

    @DBRef
    private Account reviwedUser;

    public Reviews() {
    }

    public String getId() {
        return id;
    }

    public EGrade getGrade() {
        return grade;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public Account getReviwedUser() {
        return reviwedUser;
    }

    public void setGrade(EGrade grade) {
        this.grade = grade;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
    }

    public void setReviwedUser(Account reviwedUser) {
        this.reviwedUser = reviwedUser;
    }
}
