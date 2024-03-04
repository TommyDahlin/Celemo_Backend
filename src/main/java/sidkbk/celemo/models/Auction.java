package sidkbk.celemo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "auctions")
public class Auction {
    // Everything from here to the comment of bids are required before we can post an auction to the database
    // sellerId is the user that makes the auction, the rest is self-explanatory.

    //HELENA:
    // vem är sellerId? och vem är user?
    // är det dubbelt upp?
    // eller är sellerId en annan säljare som är av typen String? och user kanske en annan säljare som är av typen User?
    // har vi två säljare?
    // wierd, right? ^^

    // ni har ju en DBRef till User men ni bör döpa om fältet till:
    // private User seller;
    // i den Usern finns ju id? Så String sellerId ska väck härifrån

    // jag vet inte om ni någon gång mappar om Enum till strängar men säger det här i alla fall att det bör ni göra
    // det blir MYCKET lättare i så fall och ni kan enkelt lösa pagination med filtrering om ni skulle vilja det
    @Id
    private String id;
    @NotBlank
    private String sellerId;
    @DBRef
    private User user;
    @NotBlank
    private String title;
    @NotBlank
    private String productDescription;
    private String productPhoto;
    private String celebrityName;
    @NotNull
    private Double startPrice;

    // Not needed for the body in postman, gets added automatically
    private LocalDate startingDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now().plusDays(7);

    // Bids
    public Double currentPrice = 0d;
    private Double endPrice = 0d;
    @DBRef
    private Bids bid;

    // Both booleans have to be true to move on to make an order.
    public boolean isFinished;
    private boolean hasBids;

    // Enum List
    public List<ECategory> categoryList = new ArrayList<>();

    public Auction(){
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setEndPrice(Double endPrice) {
        this.endPrice = endPrice;
    }

    public Bids getBid() {
        return bid;
    }

    public void setBid(Bids bid) {
        this.bid = bid;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCelebrityName() {
        return celebrityName;
    }

    public void setCelebrityName(String celebrityName) {
        this.celebrityName = celebrityName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    public boolean isHasBids() {
        return hasBids;
    }

    public void setHasBids(boolean hasBids) {
        this.hasBids = hasBids;
    }

    public List<ECategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ECategory> categoryList) {
        this.categoryList = categoryList;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }
}
