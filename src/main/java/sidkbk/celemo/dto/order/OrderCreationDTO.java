package sidkbk.celemo.dto.order;

import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

public class OrderCreationDTO {


    @CreatedDate
    private Date createdAt = new Date();
    private String auctionId;

    private String buyerUsername;


    public String getAuctionId() {
        return auctionId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }
}


