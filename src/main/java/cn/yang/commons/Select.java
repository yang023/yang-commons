package cn.yang.commons;

import org.apache.commons.lang3.ObjectUtils;

/**
 * @author yang 2019/1/26
 */
public class Select {

    private String value;

    private String text;

    public String getValue() {
        return value;
    }

    public void setValue(String code) {
        this.value = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static SelectBuilder builder() {
        return new SelectBuilder();
    }

    public static class SelectBuilder {
        private String text;
        private String value;

        public SelectBuilder text(String text) {
            this.text = text;
            return this;
        }

        public SelectBuilder value(String value) {
            this.value = value;
            return this;
        }

        public Select build() {
            Select select = new Select();
            select.setValue(ObjectUtils.defaultIfNull(this.value, ""));
            select.setText(ObjectUtils.defaultIfNull(this.text, ""));
            return select;
        }
    }
}
