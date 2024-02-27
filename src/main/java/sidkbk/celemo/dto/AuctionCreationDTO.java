package sidkbk.celemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import sidkbk.celemo.models.ECategory;

import java.util.ArrayList;
import java.util.List;

public class AuctionCreationDTO {

    // Variables
    @NotBlank
    private String sellerId;

    @NotBlank
    private String title;

    @NotBlank
    private String productDescription;
    @NotBlank
    private String productPhoto;
    @NotBlank
    private String celebrityName;

    @NotNull
    private Double startPrice;
    @NotEmpty
    private List<ECategory> categoryList = new ArrayList<>();



    // Getters
    public String getSellerId() {
        return sellerId;
    }
    public String getTitle() {
        return title;
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
    public Double getStartPrice() {
        return startPrice;
    }
    public List<ECategory> getCategoryList() {
        return categoryList;
    }

    // Setters
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public void setTitle(String title) {
        this.title = title;
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
    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }
    public void setCategoryList(List<ECategory> categoryList) {
        this.categoryList = categoryList;
    }
}
