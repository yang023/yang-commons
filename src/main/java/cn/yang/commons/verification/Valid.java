package cn.yang.commons.verification;

/**
 * @author yang 2019/3/16
 */
public class Valid {

    private static enum Code {
        /**
         * 校验成功
         */
        SUCCESS,
        /**
         * 校验失败
         */
        FAILED;
    }

    private String code;

    private String field;

    private String result;

    public String getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }

    public boolean isValid() {
        return Code.SUCCESS.toString().equalsIgnoreCase(this.code);
    }

    Valid success() {
        this.code = Code.SUCCESS.toString().toLowerCase();
        return this;
    }

    Valid failed() {
        this.code = Code.FAILED.toString().toLowerCase();
        return this;
    }

    Valid result(String result) {
        this.result = result;
        return this;
    }

    Valid field(String field) {
        this.field = field;
        return this;
    }

}
