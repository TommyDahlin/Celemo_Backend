package sidkbk.celemo.dto.search;

import jakarta.validation.constraints.NotEmpty;

public class SearchDTO {

    @NotEmpty
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
