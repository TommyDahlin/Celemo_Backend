package sidkbk.celemo.dto.order;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Date;

public class OrderCreationDTO {

    private String auctionId;
    private String sellerId;
    private String buyerId;
    private double endPrice;






    public String getAuctionId() {
        return auctionId;
    }
    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }



    public String getSellerId() {
        return sellerId;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }



    public String getBuyerId() {
        return buyerId;
    }
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

}


