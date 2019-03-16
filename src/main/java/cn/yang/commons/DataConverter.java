package cn.yang.commons;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author yang 2019/3/15
 */
public class DataConverter {

    private String json;

    private Gson gson;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public DataConverter(String json, Gson gson) {
        this.json = json;
        this.gson = gson;
    }

    public <T> T convert(Class<T> type) {
        return gson.fromJson(this.json, type);
    }

    public <K, V> Map<K, V> convert(Class<K> key, Class<V> value) {
        return gson.fromJson(this.json, new TypeToken<Map<K, V>>(){}.getType());
    }
}
