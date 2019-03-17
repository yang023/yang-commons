package cn.yang.commons.http;

/**
 * @author yang 2019/2/2
 */
public class PageQuery {

    private Integer page;
    private Integer size;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getIndex() {
        return (page - 1) * size;
    }
}
