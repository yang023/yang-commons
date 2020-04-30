package cn.yang.commons.validator.component;

import java.util.regex.Pattern;

/**
 * @author yang
 * <p>
 * 基本字符串匹配正则常量
 * <p>
 * TODO 扩展常用校验正则
 */
public final class RegexpContext {

    public final static Pattern EMPTY_STRING = Pattern.compile(".{0}");

    public final static Pattern GENERAL = Pattern.compile("^\\w+$");

    public final static Pattern NUMBER = Pattern.compile("\\d+(\\.?\\d+)");

    public final static Pattern EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])", Pattern.CASE_INSENSITIVE);

    public final static Pattern URL = Pattern.compile("[a-zA-z]+://[^\\s]*");
}
