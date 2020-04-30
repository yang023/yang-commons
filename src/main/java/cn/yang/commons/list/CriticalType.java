package cn.yang.commons.list;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yang
 */
public enum CriticalType {
    /**
     * 查询条件比较类型
     */

    EQUALS("=", "eq"),
    NOT_NULL("nn"),
    NO_EQUALS("ne", "!"),
    IN("in"),
    NOT_IN("ni"),
    LEFT_LIKE("leftLike", "ll"),
    RIGHT_LIKE("rightLike", "rl"),
    LIKE("like", "%"),
    GRATER_THEN("gt"),
    LETTER_THEN("lt");

    private String[] type;

    CriticalType(String... type) {
        this.type = type;
    }

    public static CriticalType from(String key) {
        Set<CriticalType> typeSet = Stream.of(values())
                .filter(item -> {
                    String name = item.name();
                    ArrayList<String> list = Lists.newArrayList(name);
                    list.addAll(Arrays.asList(item.type));
                    for (String itemKey : list) {
                        String temp = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, itemKey);
                        if (key.endsWith(temp)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toSet());
        if (typeSet.size() == 0) {
            return CriticalType.EQUALS;
        }
        return typeSet.iterator().next();
    }

    public String explainKey(String key) {
        String explained = key;
        String name = name();
        ArrayList<String> list = Lists.newArrayList(name);
        list.addAll(Arrays.asList(type));
        for (String itemKey : list) {
            String temp = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, itemKey);
            explained = explained.replace(temp, "");
        }
        return explained;
    }

}
