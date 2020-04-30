package cn.yang.commons.validator.component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiPredicate;

/**
 * @author yang
 * <p>
 * 匹配项格式
 */
public class ValidateItem {

    private BiPredicate<String, Map<String, String>> predicate;
    private String message;

    public ValidateItem(BiPredicate<String, Map<String, String>> predicate, String message) {
        this.predicate = Optional.of(predicate).orElse((v, f) -> true);
        this.message = Optional.ofNullable(message).orElse("字段校验失败: {field}");
    }

    public String getMessage() {
        return message;
    }

    public boolean test(String value, Map<String, String> form) {
        return this.predicate.test(value, form);
    }

    @Override
    public int hashCode() {
        return ThreadLocalRandom.current().nextInt();
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
