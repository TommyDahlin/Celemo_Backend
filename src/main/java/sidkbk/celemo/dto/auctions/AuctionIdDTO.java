package sidkbk.celemo.dto.auctions;

import jakarta.validation.constraints.NotBlank;

public class AuctionIdDTO {

    // Variables
    @NotBlank
    private String auctionId;



    // Getters & Setters
    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }
}
