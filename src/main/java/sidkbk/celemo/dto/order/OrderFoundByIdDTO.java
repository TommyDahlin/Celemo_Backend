package sidkbk.celemo.dto.order;

import jakarta.validation.constraints.NotBlank;

public class OrderFoundByIdDTO {

    @NotBlank
    private String orderId;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
