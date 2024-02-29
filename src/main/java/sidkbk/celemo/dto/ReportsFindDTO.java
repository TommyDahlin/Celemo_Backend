package sidkbk.celemo.dto;

import jakarta.validation.constraints.NotBlank;

public class ReportsFindDTO {
    @NotBlank
    public String reportsId;


    public String getReportsId() {
        return reportsId;
    }

    public void setReportsId(String reportsId) {
        this.reportsId = reportsId;
    }
}
