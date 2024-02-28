package sidkbk.celemo.dto.auctions;

import jakarta.validation.constraints.NotBlank;

public class AuctionUpdateDTO {

    //Variables
    @NotBlank
    private String auctionId;
    private String productDescription;
    private String productPhoto;
    private String celebrityName;



    // Getters
    public String getAuctionId() {
        return auctionId;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public String getProductPhoto() {
        return productPhoto;
    }
    public String getCelebrityName() {
        return celebrityName;
    }

    // Setters
    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }
    public void setCelebrityName(String celebrityName) {
        this.celebrityName = celebrityName;
    }
}
