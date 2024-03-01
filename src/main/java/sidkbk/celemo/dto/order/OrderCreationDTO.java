package sidkbk.celemo.dto.order;

import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;

public class OrderCreationDTO {



    @CreatedDate
    private Date createdAt = new Date();
    private String auctionId;
    private String sellerId;
    private String buyerId;





    public String getAuctionId() {
        return auctionId;
    }
    public String getSellerId() {
        return sellerId;
    }
    public String getBuyerId() {
        return buyerId;
    }
    public Date getCreatedAt() {
        return createdAt;
    }




    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}


