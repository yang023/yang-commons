package cn.yang.commons.http;

import java.util.List;

/**
 * @author yang 2019/1/20
 */
public class PageResponse<T> extends AbstractResponse {
    private List<T> data;
    private Long total;
    private Integer size;
    private Integer page;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotalPage() {
        if (size == 0) {
            return 0L;
        }
        return Math.abs(total / size) + (total % size == 0 ? 0 : 1);
    }

    public boolean isHasPrePage() {
        return page > 1;
    }

    public boolean isHasNextPage() {
        return Long.valueOf(page) < getTotalPage();
    }

    public static ResponseBuilder code(Integer code) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.code(code);
        return builder;
    }

    public static ResponseBuilder message(String message) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.message(message);
        return builder;
    }

    public static <T> PageResponse<T> ok(Integer page, Integer size, Long total, List<T> data, String message) {
        return PageResponse.code(ResponseCode.SUCCESS).message(message).pageResponse(page, size, total, data);
    }

    public static <T> PageResponse<T> ok(Integer page, Integer size, Long total, List<T> list) {
        return PageResponse.ok(page, size, total, list, null);
    }

    public static <T> PageResponse<T> failed(String message) {
        return PageResponse.code(ResponseCode.FAILED).message(message).pageResponse(0, 0, 0L, null);
    }

    public static ResponseBuilder failed() {
        return PageResponse.failed();
    }

    public static ResponseBuilder ok() {
        return PageResponse.ok();
    }

    public static <T> PageResponse<T> ok(PageInfo<T> pageInfo) {
        return PageResponse.ok(pageInfo, null);
    }

    public static <T> PageResponse<T> ok(PageInfo<T> pageInfo, String message) {
        return PageResponse.ok(
                pageInfo.getPage(),
                pageInfo.getSize(),
                pageInfo.getTotal(),
                pageInfo.getList(),
                message
        );
    }


}
