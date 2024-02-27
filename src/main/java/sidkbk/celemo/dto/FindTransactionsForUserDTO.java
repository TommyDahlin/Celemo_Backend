package sidkbk.celemo.dto;

import jakarta.validation.constraints.NotBlank;

public class FindTransactionsForUserDTO {

    // Variables
    @NotBlank
    public String userId;

    // Getters & Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
