package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;

public class ModifyUserFavouritesDTO {
    @NotBlank
    private String userId;
    @NotBlank
    private String auctionId;


    public String getUserId() {
        return userId;
    }


    public String getAuctionId() {
        return auctionId;
    }



}
