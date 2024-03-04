package sidkbk.celemo.dto.Bids;

public class BidsDTO {


    // Data transfer object/ User and Auction


    private double startBid;
    private double maxBid;

    private String userId;

    private String auctionId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }


    public double getStartBid() {
        return startBid;
    }

    public void setStartPrice(double startBid) {
        this.startBid = startBid;
    }

    public double getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(double maxBid) {
        this.maxBid = maxBid;
    }




}
