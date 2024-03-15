package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("transactions")
public class Transactions {

    // Varaibles
    @Id
    private String id;

    @DBRef
    private User user;

    private Double transactionAmount;

    @CreatedDate
    private Date transactionDate = new Date();

    // Constructors
    public Transactions() {
    }

    // Getters

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    // Setters

    public void setUser(User user) {
        this.user = user;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
