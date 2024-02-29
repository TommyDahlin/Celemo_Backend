package sidkbk.celemo.dto.order;

public class PreviousPurchaseFromOrderDTO {

    private String auctionId;
    private String userId;    
    private String buyerId;







    public String getAuctionId() {
        return auctionId;
    }
    public String getUserId() {
        return userId;
    }
    public String getBuyerId() {
        return buyerId;
    }




    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
}
