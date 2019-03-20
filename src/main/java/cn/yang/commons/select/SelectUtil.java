package cn.yang.commons.select;

import cn.yang.commons.http.PageInfo;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author yang 2019/3/20
 */
public class SelectUtil {

    private OptionProvider provider;

    public SelectUtil addProvider(OptionProvider... providers) {
        Stream<OptionProvider> stream = Stream.of(providers);
        stream.forEach(provider -> {
            provider.setType();
            if (Objects.isNull(this.provider)) {
                this.provider = provider;
            } else {
                this.provider.setNext(provider);
            }
        });
        return this;
    }

    public PageInfo<Option> options(String type, Map<String, Object> params) {
        List<Option> optionList;
        if (Objects.isNull(this.provider)) {
            optionList = new LinkedList<>();
        } else {
            optionList = this.provider.doFetch(type, params);
        }

        if (Objects.isNull(params)) {
            params = new HashMap<>(0);
        }

        return PageInfo.<Option>builder()
                .page((Long) params.getOrDefault("page", 0L))
                .size((Long) params.getOrDefault("size", 0L))
                .total((long) optionList.size())
                .list(optionList)
                .build();
    }

}
