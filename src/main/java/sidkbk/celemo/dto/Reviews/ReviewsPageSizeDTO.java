package sidkbk.celemo.dto.Reviews;

public class ReviewsPageSizeDTO {

    private int pageSize = 2; // Change later to a better value like 10

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
