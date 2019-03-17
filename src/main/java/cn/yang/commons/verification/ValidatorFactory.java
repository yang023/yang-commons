package cn.yang.commons.verification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yang 2019/3/16
 */
public class ValidatorFactory {

    private static final Map<String, Rule> RULES = new ConcurrentHashMap<>();

    public void configRules(Map<String, Rule> rules) {
        rules.forEach(RULES::put);
    }

    public Validator getValidator(String name) {
        Rule rule = getRule(name);
        return getValidator(rule);
    }

    public List<Validator> getValidators(String ...names) {
        List<Validator> validators = new ArrayList<>(names.length);
        Arrays.asList(names).forEach(name -> validators.add(getValidator(name)));
        return validators;
    }

    public static Validator getValidator(Rule rule) {
        return new Validator(rule);
    }

    private Rule getRule(String name) {
        return RULES.get(name);
    }
}
