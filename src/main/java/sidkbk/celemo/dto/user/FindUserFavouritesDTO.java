package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;
import sidkbk.celemo.models.Auction;

import java.util.ArrayList;

public class FindUserFavouritesDTO {

    @NotBlank
    private String userId;
    private ArrayList<Auction> favouriteAuctions = new ArrayList<Auction>();

    public String getUserId() {
        return userId;
    }

    public ArrayList<Auction> getFavouriteAuctions() {
        return favouriteAuctions;
    }


}
