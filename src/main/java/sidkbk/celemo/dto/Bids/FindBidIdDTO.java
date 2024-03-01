package sidkbk.celemo.dto.Bids;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

public class FindBidIdDTO {

    // Variables
    @NotBlank
    private String bidId;


    // Getters & Setters
    public String getbidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }




}
