package cn.yang.commons.verification;

/**
 * @author yang 2019/3/16
 */
public interface Rule {

    <T> String validate(T value);
}
