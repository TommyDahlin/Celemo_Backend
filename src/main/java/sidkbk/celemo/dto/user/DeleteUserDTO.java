package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;

public class DeleteUserDTO {
    @NotBlank(message = "Have to have an id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
