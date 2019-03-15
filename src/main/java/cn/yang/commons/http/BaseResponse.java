package cn.yang.commons.http;

/**
 * @author yang 2019/1/20
 */
public class BaseResponse<T> extends AbstractResponse {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    public static <T> BaseResponse<T> ok(T data, String message) {
        return BaseResponse.code(ResponseCode.SUCCESS).message(message).baseResponse(data);
    }

    public static <T> BaseResponse<T> ok(T data) {
        return BaseResponse.ok(data, null);
    }

    public static <T> BaseResponse<T> failed(T data, String message) {
        return BaseResponse.code(ResponseCode.FAILED).message(message).baseResponse(data);
    }

    public static <T> BaseResponse<T> failed(T data) {
        return BaseResponse.failed(data, null);
    }
}
