package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;

public class FindAllByIdDTO {

    @NotBlank
    private String id;

    public String getId() {
        return id;
    }
}
