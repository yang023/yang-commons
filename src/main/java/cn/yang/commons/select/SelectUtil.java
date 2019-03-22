package cn.yang.commons.select;

import cn.yang.commons.http.PageInfo;

import java.util.*;

/**
 * @author yang 2019/3/20
 */
public class SelectUtil {

    private OptionProvider provider;

    public SelectUtil addProvider(OptionProvider provider) {
        provider.setType();
        if (Objects.isNull(this.provider)) {
            this.provider = provider;
        } else {
            this.provider.setNext(provider);
        }
        return this;
    }

    public SelectUtil addProvider(OptionProvider... providers) {
        return this.addProvider(Arrays.asList(providers));
    }

    public SelectUtil addProvider(List<OptionProvider> providers) {
        providers.forEach(this::addProvider);
        return this;
    }

    public PageInfo<Option> options(String type, Map<String, Object> params) {
        PageInfo<Option> optionList;
        if (Objects.isNull(this.provider)) {
            optionList = PageInfo.empty();
        } else {
            if (Objects.isNull(params)) {
                params = new HashMap<>(0);
            }
            optionList = this.provider.doFetch(type, params);
        }

        return optionList;
    }

}
