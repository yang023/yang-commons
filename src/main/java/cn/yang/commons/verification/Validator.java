package cn.yang.commons.verification;

import com.google.common.base.Strings;

/**
 * @author yang 2019/3/16
 */
public class Validator {

    private Rule rule;

    Validator(Rule rule) {
        this.rule = rule;
    }

    public Valid validate(Object value, String field, String message) {
        String result = rule.validate(value);
        Valid valid = new Valid();
        if (Strings.isNullOrEmpty(result)) {
            valid = valid.success();
        } else {
            valid = valid.failed();
            if (!Strings.isNullOrEmpty(message)) {
                valid = valid.result(message);
            } else {
                valid.result(result);
            }
        }

        return valid.field(field);
    }

}
