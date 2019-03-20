package cn.yang.commons.select;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yang 2019/3/20
 */
public abstract class OptionProvider {

    private OptionProvider next;

    private String type;

    void setNext(OptionProvider next) {
        this.next = next;
    }

    void setType() {
        this.type = this.type();
    }

    protected abstract String type();

    protected abstract List<Option> fetch(Map<String, Object> param);

    public List<Option> doFetch(String type, Map<String, Object> param) {
        if (type.equals(this.type)) {
            return this.fetch(param);
        } else if (!Objects.isNull(this.next)) {
            return this.next.doFetch(type, param);
        } else {
            return new LinkedList<>();
        }
    }

}
