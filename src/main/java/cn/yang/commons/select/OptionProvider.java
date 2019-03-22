package cn.yang.commons.select;

import cn.yang.commons.http.PageInfo;

import java.util.*;

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

    protected abstract PageInfo<Option> fetch(Map<String, Object> param);

    public final PageInfo<Option> doFetch(String type, Map<String, Object> param) {
        if (type.equals(this.type)) {
            return this.fetch(param);
        } else if (!Objects.isNull(this.next)) {
            return this.next.doFetch(type, param);
        } else {
            return PageInfo.empty();
        }
    }

}
