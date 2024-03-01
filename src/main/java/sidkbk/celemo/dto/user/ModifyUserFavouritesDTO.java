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
//setUserId

    public String getAuctionId() {
        return auctionId;
    }


    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }
}
