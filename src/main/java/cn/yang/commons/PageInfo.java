package cn.yang.commons;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author yang
 * <p>
 * 分页结果
 */
public class PageInfo<T> {
    @JSONField(name = "page")
    private Integer page;
    @JSONField(name = "size")
    private Integer size;
    @JSONField(name = "total")
    private Long total;
    @JSONField(name = "list")
    private List<T> list;
    @JSONField(name = "has_next")
    private Boolean hasNext;

    public PageInfo(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public static <T> PageInfo<T> of(int page, int size) {
        return new PageInfo<T>(page, size);
    }

    public static <T> PageInfo<T> of(long page, long size) {
        return new PageInfo<T>(Long.valueOf(page).intValue(), Long.valueOf(size).intValue());
    }

    public PageInfo<T> setTotal(long total) {
        this.total = total;
        this.hasNext = Math.ceil(total / size) > page;
        return this;
    }

    public Long getOffset() {
        return (long) ((page - 1) * size);
    }

    public PageInfo<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getList() {
        return list;
    }

    public Boolean hasNext() {
        return hasNext;
    }
}
