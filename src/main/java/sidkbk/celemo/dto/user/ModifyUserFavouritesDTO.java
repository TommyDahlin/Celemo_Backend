package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;
import sidkbk.celemo.models.Auction;

public class ModifyUserFavouritesDTO {
    @NotBlank
    private String userId;
    @NotBlank
    private Auction auctionId;


    public String getUserId() {
        return userId;
    }


    public Auction getAuctionId() {
        return auctionId;
    }


    public void setAuctionId(Auction auctionId) {
        this.auctionId = auctionId;
    }
}
