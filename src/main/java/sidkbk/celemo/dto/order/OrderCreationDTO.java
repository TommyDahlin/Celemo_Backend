package sidkbk.celemo.dto.order;

import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;

public class OrderCreationDTO {



    @CreatedDate
    private Date createdAt = new Date();
    private String auctionId;
    private String buyerId;





    public String getAuctionId() {
        return auctionId;
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
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}


