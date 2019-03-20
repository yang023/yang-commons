package cn.yang.commons.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yang 2019/1/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private T data;

    private String message;

    private Integer code;

    public static <T> BaseResponseBuilder ok() {
        return BaseResponse.<T>builder()
                .code(ResponseCode.SUCCESS);
    }

    public static <T> BaseResponseBuilder failed() {
        return BaseResponse.<T>builder()
                .code(ResponseCode.FAILED);
    }
}
