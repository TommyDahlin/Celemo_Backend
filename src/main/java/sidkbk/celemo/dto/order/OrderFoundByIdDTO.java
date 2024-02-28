package sidkbk.celemo.dto.order;

import jakarta.validation.constraints.NotBlank;

public class OrderFoundByIdDTO {

    @NotBlank
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
