package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;

public class FindUserIdDTO {

    @NotBlank
    private String userId;
    private ArrayList<String> favouriteAuctions = new ArrayList<String>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getFavouriteAuctions() {
        return favouriteAuctions;
    }

    public void setFavouriteAuctions(ArrayList<String> favouriteAuctions) {
        this.favouriteAuctions = favouriteAuctions;
    }
}
