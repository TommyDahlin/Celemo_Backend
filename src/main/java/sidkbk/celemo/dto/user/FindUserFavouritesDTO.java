package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;

public class FindUserFavouritesDTO {

    @NotBlank
    private String userId;


    public String getUserId() {
        return userId;
    }




}
