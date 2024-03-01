package sidkbk.celemo.dto.Reviews;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewsSortLowHighDTO {

    // Variables
    @NotBlank
    private String userId;
    @NotNull
    private String lowOrHigh; // LOW or HIGH

    // Getters
    public String getUserId() {
        return userId;
    }
    public String getLowOrHigh() {
        if (this.lowOrHigh.toUpperCase().equals("LOW")) {
            return "LOW";
        } else {
            return "HIGH";
        }
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setLowOrHigh(String lowOrHigh) {
        this.lowOrHigh = lowOrHigh;
    }
}
