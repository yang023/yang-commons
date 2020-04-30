package cn.yang.commons.validator.component;

/**
 * @author yang
 * <p>
 * 错误信息模板
 */
public class ErrorMessageTemplate {

    public final static String DEFAULT_INVALID_ERROR = "字段校验失败: {field}[{value}]";
    public final static String EMPTY_STRING_ERROR = "缺失字段: {field}[{value}]";
    public final static String NOT_NUMBER_ERROR = "{field}[{value}] 不是数字格式";
    public final static String NOT_EMAIL_ERROR = "{field}[{value}] 不是邮箱格式";
    public final static String NOT_URL_ERROR = "{field}[{value}] 不是URL格式";
    public final static String NOT_IN_RANGE_NUMBER_ERROR = "{field}[{value}] 不在范围内: [{min}, {max}]";
    public final static String NUMBER_MINIMUM_ERROR = "{field}[{value}] 不在范围内: [{min}, +∞)";
    public final static String NUMBER_MAXIMUM_ERROR = "{field}[{value}] 不在范围内: (-∞, {max}]";

    public final static String FIELD_TEMPLATE = "\\{field}";
    public final static String VALUE_TEMPLATE = "\\{value}";
    public final static String RANGE_MAX_KEY = "\\{max}";
    public final static String RANGE_MIN_KEY = "\\{min}";
}
