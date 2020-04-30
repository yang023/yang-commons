package cn.yang.commons.list;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author yang
 * <p>
 * 查询条件
 */
@Data
@Accessors(chain = true)
@ToString
public class CriticalInfo {
    private String key;
    private String source;
    private Object value;
    private CriticalType type;

    public int getIntValue() {
        return JSONObject.parseObject(jsonString(), Integer.class);
    }

    public String getStringValue() {
        return JSONObject.parseObject(jsonString(), String.class);
    }

    public boolean getBooleanValue() {
        return JSONObject.parseObject(jsonString(), Boolean.class);
    }

    public long getLongValue() {
        return JSONObject.parseObject(jsonString(), Long.class);
    }

    public List<String> getArrayValue() {
        return JSONObject.parseArray(jsonString(), String.class);
    }

    private String jsonString() {
        return JSONObject.toJSONString(this.value);
    }

    @Override
    public int hashCode() {
        return this.source.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CriticalInfo)) {
            return false;
        }
        CriticalInfo criticalInfo = (CriticalInfo) obj;
        return this.key.equals(criticalInfo.getKey());
    }

}
