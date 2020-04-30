package cn.yang.commons.validator;

import cn.yang.commons.validator.component.ErrorMessageTemplate;
import cn.yang.commons.validator.component.RegexpContext;
import cn.yang.commons.validator.component.ValidateItem;
import cn.yang.commons.validator.exceptions.InvalidException;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author yang
 * <p>
 * 表单动态校验器，<block>JSR303</block> 校验规则写死配置，不能解决 web 表单动态校验问题，提供可配置的 web 表单校验工具
 * <p>
 * TODO 连接数据库保存配置规则，通过注解方式动态获取校验规则并校验 web 表单
 */
public class Validator {

    private Map<String, String> obj;
    private Map<String, Set<ValidateItem>> items;

    private Validator(Map<String, String> obj) {
        this.obj = obj;
        this.items = new LinkedHashMap<>();
    }

    public static Validator of(Map<String, String> obj) {
        return new Validator(obj);
    }

    /**
     * 设置校验字段规则
     *
     * @param field     字段名
     * @param predicate 校验方式
     * @param message   错误信息
     * @return 校验器对象
     */
    public Validator on(String field, BiPredicate<String, Map<String, String>> predicate, String message) {
        ValidateItem item = new ValidateItem(predicate, defaultErrorMessage(message, ErrorMessageTemplate.DEFAULT_INVALID_ERROR));
        Set<ValidateItem> itemSet = this.items.get(field);
        if (Objects.isNull(itemSet)) {
            itemSet = new LinkedHashSet<>();
            this.items.put(field, itemSet);
        }
        itemSet.add(item);
        return this;
    }

    /**
     * 设置校验字段规则，使用默认错误信息
     *
     * @param field     字段名出
     * @param predicate 校验方式
     * @return 校验器对象
     */
    public Validator on(String field, BiPredicate<String, Map<String, String>> predicate) {
        return this.on(field, predicate, null);
    }

    /**
     * 必填字段校验
     *
     * @param field   字段名
     * @param message 错误信息
     * @return 校验器对象
     */
    public Validator required(String field, String message) {
        return this.on(field, (value, form) -> !regexpMatcher(value, RegexpContext.EMPTY_STRING), defaultErrorMessage(message, ErrorMessageTemplate.EMPTY_STRING_ERROR));
    }

    /**
     * 使用默认错误方式的必填字段校验
     *
     * @param field 字段名
     * @return 校验器对象
     */
    public Validator required(String field) {
        return this.required(field, null);
    }

    /**
     * 数字字段校验
     *
     * @param field   字段名
     * @param message 错误信息
     * @return 校验器对象
     */
    public Validator number(String field, String message) {
        return this.on(field, (value, form) -> regexpMatcher(value, RegexpContext.NUMBER), defaultErrorMessage(message, ErrorMessageTemplate.NOT_NUMBER_ERROR));
    }

    /**
     * 使用默认错误方式的数字字段校验
     *
     * @param field 字段名
     * @return 校验器对象
     */
    public Validator number(String field) {
        return this.number(field, null);
    }

    /**
     * 邮箱字段校验
     *
     * @param field   字段名
     * @param message 错误信息
     * @return 校验器对象
     */
    public Validator email(String field, String message) {
        return this.on(field, (value, form) -> regexpMatcher(value, RegexpContext.EMAIL), defaultErrorMessage(message, ErrorMessageTemplate.NOT_EMAIL_ERROR));
    }

    /**
     * 使用默认错误方式的邮箱字段校验
     *
     * @param field 字段名
     * @return 校验器对象
     */
    public Validator email(String field) {
        return this.email(field, null);
    }

    /**
     * URL字段校验
     *
     * @param field   字段名
     * @param message 错误信息
     * @return 校验器对象
     */
    public Validator url(String field, String message) {
        return this.on(field, (value, form) -> regexpMatcher(value, RegexpContext.URL), defaultErrorMessage(message, ErrorMessageTemplate.NOT_URL_ERROR));
    }

    /**
     * 使用默认错误方式的URL字段校验
     *
     * @param field 字段名
     * @return 校验器对象
     */
    public Validator url(String field) {
        return this.url(field, null);
    }

    /**
     * 邮箱字段校验
     *
     * @param field   字段名
     * @param message 错误信息
     * @return 校验器对象
     */
    public Validator general(String field, String message) {
        return this.on(field, (value, form) -> regexpMatcher(value, RegexpContext.GENERAL), defaultErrorMessage(message, ErrorMessageTemplate.DEFAULT_INVALID_ERROR));
    }

    /**
     * 使用默认错误方式的邮箱字段校验
     *
     * @param field 字段名
     * @return 校验器对象
     */
    public Validator general(String field) {
        return this.general(field, null);
    }

    /**
     * 数字范围大小校验
     *
     * @param min 数字最小值
     * @param max 数字最大值
     * @return 校验器对象
     */
    public Validator range(String field, Double min, Double max, String message) {
        Map<String, String> replacement = new LinkedHashMap<>(2);
        replacement.put(ErrorMessageTemplate.RANGE_MAX_KEY, String.valueOf(max));
        replacement.put(ErrorMessageTemplate.RANGE_MIN_KEY, String.valueOf(min));
        return this.on(field, (value, form) -> {
            if (!regexpMatcher(value, RegexpContext.NUMBER)) {
                return false;
            }
            Double number = Double.valueOf(value);
            return (number.compareTo(max) <= 0 && number.compareTo(min) >= 0);
        }, handleTemplate(defaultErrorMessage(message, ErrorMessageTemplate.NOT_IN_RANGE_NUMBER_ERROR), replacement));
    }

    /**
     * 使用默认错误方式的数字范围大小校验
     *
     * @param field 字段名
     * @return 校验器对象
     */
    public Validator range(String field, Double min, Double max) {
        return this.range(field, min, max, null);
    }

    /**
     * 数字最大值校验
     *
     * @param max 数字最大值
     * @return 校验器对象
     */
    public Validator max(String field, Double max, String message) {
        Map<String, String> replacement = new LinkedHashMap<>(1);
        replacement.put(ErrorMessageTemplate.RANGE_MAX_KEY, String.valueOf(max));
        return this.on(field, (value, form) -> {
            if (!regexpMatcher(value, RegexpContext.NUMBER)) {
                return false;
            }
            Double number = Double.valueOf(value);
            return number.compareTo(max) <= 0;
        }, handleTemplate(defaultErrorMessage(message, ErrorMessageTemplate.NUMBER_MAXIMUM_ERROR), replacement));
    }

    /**
     * 使用默认错误方式的数字最大值校验
     *
     * @param field 字段名
     * @return 校验器对象
     */
    public Validator max(String field, Double max) {
        return this.max(field, max, null);
    }

    /**
     * 数字最小值校验
     *
     * @param min 数字最小值
     * @return 校验器对象
     */
    public Validator min(String field, Double min, String message) {
        Map<String, String> replacement = new LinkedHashMap<>(1);
        replacement.put(ErrorMessageTemplate.RANGE_MIN_KEY, String.valueOf(min));
        return this.on(field, (value, form) -> {
            if (!regexpMatcher(value, RegexpContext.NUMBER)) {
                return false;
            }
            Double number = Double.valueOf(value);
            return number.compareTo(min) >= 0;
        }, handleTemplate(defaultErrorMessage(message, ErrorMessageTemplate.NUMBER_MINIMUM_ERROR), replacement));
    }

    /**
     * 使用默认错误方式的数字最小值校验
     *
     * @param field 字段名
     * @return 校验器对象
     */
    public Validator min(String field, Double min) {
        return this.min(field, min, null);
    }


    /**
     * 执行校验，存在校验失败字段，抛出 {@link InvalidException} 异常
     */
    public void validate() {
        String key;
        ValidateItem item;
        for (Map.Entry<String, Set<ValidateItem>> entry : this.items.entrySet()) {
            key = entry.getKey();
            Set<ValidateItem> items = entry.getValue();
            String value = Optional.ofNullable(obj.get(key)).orElse("");
            for (ValidateItem validateItem : items) {
                item = validateItem;
                boolean test = item.test(value, this.obj);
                if (!test) {
                    throw new InvalidException(errorMessage(key, value, item.getMessage()));
                }
            }
        }
    }

    /**
     * 执行校验，存在校验失败字段，抛出 {@link InvalidException} 的异常通过回调函数处理
     *
     * @param callback {@link InvalidException} 异常处理回调
     * @param <R>      返回值类型
     * @return callback {@link InvalidException} 的返回值
     */
    public <R> R validate(Function<InvalidException, R> callback) {
        try {
            this.validate();
            return null;
        } catch (InvalidException e) {
            return callback.apply(e);
        }
    }

    /**
     * 执行校验，存在校验失败字段，抛出 {@link InvalidException} 的异常通过回调函数处理
     *
     * @param callback {@link InvalidException} 异常处理回调
     */
    public void validate(Consumer<InvalidException> callback) {
        try {
            this.validate();
        } catch (InvalidException e) {
            callback.accept(e);
        }
    }

    private static String errorMessage(String field, String value, String template) {
        return template.replaceAll(ErrorMessageTemplate.FIELD_TEMPLATE, field).replaceAll(ErrorMessageTemplate.VALUE_TEMPLATE, value);
    }

    private static boolean regexpMatcher(String value, Pattern pattern) {
        return pattern.matcher(value).matches();
    }

    private static String defaultErrorMessage(String value, String defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }

    private static String handleTemplate(String template, Map<String, String> replacement) {
        AtomicReference<String> temp = new AtomicReference<>(template);
        replacement.forEach((key, value) -> temp.set(temp.get().replaceAll(key, value)));
        return temp.get();
    }
}
