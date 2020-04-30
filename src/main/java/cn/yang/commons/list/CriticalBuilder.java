package cn.yang.commons.list;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yang
 * <p>
 * 查询条件构造器
 * <p>
 * TODO 暂提交，后续移除mybatis依赖
 */
public class CriticalBuilder {
    public static final String PAGE_NO_KEY = "page";
    public static final String PAGE_SIZE_KEY = "size";

    private static final String TEMPLATE_TABLE_ALIAS = "info.";
    private static final String QUERY_ITEM_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String ARRAY_SEPARATOR = ",";

    private Set<CriticalInfo> query;

    private Map<String, String> values;

    private CriticalBuilder(Map<String, String> map) {
        query = Sets.newHashSet();
        values = Maps.newHashMap();
        map.forEach((key, value) -> {
            values.put(key, value);
            CriticalType type = CriticalType.from(key);
            if (type != null) {
                String explainKey = type.explainKey(key);
                Object criticalValue = parseValue(value, type);
                query.add(new CriticalInfo()
                        .setKey(explainKey)
                        .setSource(value)
                        .setType(type)
                        .setValue(criticalValue));
            } else if (key.equals(PAGE_SIZE_KEY) || key.equals(PAGE_NO_KEY)) {
                query.add(new CriticalInfo()
                        .setKey(key)
                        .setSource(value)
                        .setType(CriticalType.EQUALS)
                        .setValue(value));
            }
        });
    }

    private CriticalBuilder(String queryString) {
        query = Sets.newHashSet();
        values = Maps.newHashMap();
        String[] queryItems = queryString.split(QUERY_ITEM_SEPARATOR);
        Map<String, String> map = Maps.newLinkedHashMapWithExpectedSize(queryItems.length);
        Stream.of(queryItems).forEach(item -> {
            String[] keyValueMap = item.split(KEY_VALUE_SEPARATOR);
            map.put(keyValueMap[0], keyValueMap[1]);
        });
        new CriticalBuilder(map);
    }

    public static CriticalBuilder create(String queryString) {
        return new CriticalBuilder(queryString);
    }

    public static CriticalBuilder create(Map<String, String> map) {
        return new CriticalBuilder(map);
    }

    private Set<CriticalInfo> getCritical() {
        return this.query.stream()
                .filter(item -> {
                    String key = item.getKey();
                    return !(key.equals(PAGE_SIZE_KEY) || key.equals(PAGE_NO_KEY));
                }).collect(Collectors.toSet());
    }

    private Object parseValue(String value, CriticalType type) {
        try {
            return JSONObject.parseArray(value, String.class);
        } catch (RuntimeException e) {
            // 根据匹配类型判断是否转换为纯字符串
            if (type.equals(CriticalType.IN) || type.equals(CriticalType.NOT_IN)) {

                String[] split = value.split(ARRAY_SEPARATOR);
                return Lists.newArrayList(split);
            }
            return value;
        }
    }

    private String aliasKey(String name, boolean alias) {
        return alias ? TEMPLATE_TABLE_ALIAS + "`" + name + "`" : "`" + name + "`";
    }

    public String getQueryValue(String key) {
        return this.values.get(key);
    }

    public PageParam pageParam() {
        Set<CriticalInfo> collect = this.query.stream()
                .filter(item -> {
                    String key = item.getKey();
                    return key.equals(PAGE_SIZE_KEY) || key.equals(PAGE_NO_KEY);
                }).collect(Collectors.toSet());
        Iterator<CriticalInfo> iterator = collect.iterator();
        int page = -1;
        int size = 10;
        while (iterator.hasNext()) {
            CriticalInfo next = iterator.next();
            if (next.getKey().equals(PAGE_NO_KEY)) {
                page = next.getIntValue();
            }
            if (next.getKey().equals(PAGE_SIZE_KEY)) {
                size = next.getIntValue();
            }
        }
        return new PageParam(page, size);
    }

    public <T> IPage<T> page() {
        PageParam pageParam = pageParam();
        return new Page<>(pageParam.getPage(), pageParam.getSize());
    }

    public <T> QueryWrapper<T> queryWrapper(Class<T> type, boolean alias, String... ignore) {
        QueryWrapper<T> wrapper = Wrappers.query();
        List<String> ignoreKeys = Arrays.asList(ignore);
        getCritical().forEach((key) -> {
            String aliasKey = aliasKey(key.getKey(), alias);
            if (!ignoreKeys.contains(key.getKey())) {
                switch (key.getType()) {
                    case NOT_NULL:
                        wrapper.isNotNull(aliasKey);
                        break;
                    case LIKE:
                        wrapper.like(aliasKey, key.getValue());
                        break;
                    case IN:
                        wrapper.in(aliasKey, key.getArrayValue());
                        break;
                    case NO_EQUALS:
                        wrapper.ne(aliasKey, key.getValue());
                        break;
                    case GRATER_THEN:
                        wrapper.gt(aliasKey, key.getValue());
                        break;
                    case LETTER_THEN:
                        wrapper.lt(aliasKey, key.getValue());
                        break;
                    case RIGHT_LIKE:
                        wrapper.likeRight(aliasKey, key.getValue());
                        break;
                    case LEFT_LIKE:
                        wrapper.likeLeft(aliasKey, key.getValue());
                        break;
                    case NOT_IN:
                        wrapper.notIn(aliasKey, key.getArrayValue());
                        break;
                    case EQUALS:
                    default:
                        wrapper.eq(aliasKey, key.getValue());
                        break;
                }
            }
        });
        return wrapper;
    }

    public <T> QueryWrapper<T> queryWrapper(Class<T> type, String... ignore) {
        return queryWrapper(type, true, ignore);
    }

    public QueryWrapper<Object> queryWrapper(boolean alias, String... ignore) {
        return queryWrapper(Object.class, alias, ignore);
    }

    public QueryWrapper<Object> queryWrapper(String... ignore) {
        return queryWrapper(Object.class, true, ignore);
    }

}
