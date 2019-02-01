package cn.yang.commons;

/**
 * @author yang 2019/2/1
 */
public class DAOException extends RuntimeException {

    public DAOException(Throwable cause) {
        super(cause.getLocalizedMessage(), cause);
    }
}
