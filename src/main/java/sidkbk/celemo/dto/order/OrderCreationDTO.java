package sidkbk.celemo.dto.order;

import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;

public class OrderCreationDTO {


    private String test;
//    private double commission;
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
    public String getTest() {
        return test;
    }
//    public double getCommission() {
//        return commission;
//    }




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
    public void setTest(String test) {
        this.test = test;
    }
//    public void setCommission(double commission) {
//        this.commission = commission;
//    }
}


