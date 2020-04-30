package cn.yang.commons;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author yang
 * <p>
 * 接口格式响应体结构
 */
public class Response<T> {
    private final static String CODE_KEY = "code";
    private final static String MSG_KEY = "message";
    private final static String DATA_KEY = "data";

    private Integer code;
    private String message;
    private T data;

    private Response() {
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Map<String, Object> bodyMap() {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put(CODE_KEY, this.code);
        map.put(MSG_KEY, this.message);
        map.put(DATA_KEY, this.data);
        return map;
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<>();
    }

    public static class ResponseBuilder<T> {
        private Integer code;
        private String message;
        private T data;

        public ResponseBuilder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public ResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Response<T> build() {
            Response<T> response = new Response<>();
            response.code = this.code;
            response.message = this.message;
            response.data = this.data;
            return response;
        }
    }

}
