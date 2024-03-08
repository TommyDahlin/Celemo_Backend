package sidkbk.celemo.dto.search;

public class SearchDTO{

    private String search;

    private short pageSize = 5; // OPTIONAL

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public short getPageSize() {
        return pageSize;
    }

    public void setPageSize(short pageSize) {
        this.pageSize = pageSize;
    }
}
