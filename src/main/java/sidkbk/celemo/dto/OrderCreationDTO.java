package sidkbk.celemo.dto;

import java.util.Date;

public class OrderCreationDTO {

    private String auctionId;
    private String sellerId;
    private String buyerId;
    private double endPrice;
    private Date endDate;





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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

}


