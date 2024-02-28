package sidkbk.celemo.dto.transactions;

import jakarta.validation.constraints.NotBlank;

public class DeleteTransactionDTO {

    // Variables
    @NotBlank
    private String transactionId;



    // Getters & Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
