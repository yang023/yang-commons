package cn.yang.commons;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author yang
 */
public class MapConverter<T> {

    private Class<T> type;

    private MapConverter(Class<T> type) {
        this.type = type;
    }

    public static <T> MapConverter<T> of(Class<T> type) {
        return new MapConverter<>(type);
    }

    public Map<String, Object> toMap(T instance) {
        String jsonString = toJsonString(instance);
        return JSONObject.parseObject(jsonString);
    }

    public T toInstance(Map<String, Object> map) {
        String jsonString = toJsonString(map);
        return JSONObject.parseObject(jsonString, type);
    }

    private String toJsonString(T instance) {
        return JSONObject.toJSONString(instance);
    }

    private String toJsonString(Map<String, Object> map) {
        return JSONObject.toJSONString(map);
    }
}
