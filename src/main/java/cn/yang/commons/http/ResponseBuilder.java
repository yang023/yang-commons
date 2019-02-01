package cn.yang.commons.http;

import java.util.List;

/**
 * @author yang 2019/1/20
 */
public class ResponseBuilder {

    private Integer code;

    private String message;

    private Integer page;

    private Integer size;

    private Long total;

    public ResponseBuilder code(Integer code) {
        this.code = code;
        return this;
    }

    public ResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ResponseBuilder page(Integer page, Integer size) {
        this.page = page;
        this.size = size;
        return this;
    }

    public ResponseBuilder total(Long total) {
        this.total = total;
        return this;
    }

    public <T> BaseResponse<T> failed(T data, String message) {
        code(ResponseCode.FAILED).message(message);
        return baseResponse(data);
    }

    public <T> BaseResponse<T> baseResponse(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public <T> PageResponse<T> pageResponse(List<T> data) {
        PageResponse<T> response = new PageResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setPage(page);
        response.setSize(size);
        response.setTotal(total);
        response.setData(data);
        return response;
    }

    public <T> PageResponse<T> pageResponse(Integer page, Integer size, Long total, List<T> list) {
        page(page, size).total(total);
        return pageResponse(list);
    }
}
