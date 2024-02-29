package sidkbk.celemo.dto;

import jakarta.validation.constraints.NotBlank;

public class ReportsPutDTO {


    @NotBlank
    private String reportsId;


    private String content;


    public String getReportsId() {
        return reportsId;
    }

    public void setReportsId(String reportsId) {
        this.reportsId = reportsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
