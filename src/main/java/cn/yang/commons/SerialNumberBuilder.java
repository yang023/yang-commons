package cn.yang.commons;

import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yang 2019/3/1
 */
public class SerialNumberBuilder {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static char[] CHAR_TEMP;

    static {
        char[] temp = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        CHAR_TEMP = new char[temp.length];
        for (int i = 0; i < temp.length; i++) {
            CHAR_TEMP[i] = temp[RandomUtils.nextInt(0, temp.length - 1)];
        }
    }

    public static String create(String prefix, int randomLength) {
        StringBuilder builder = new StringBuilder(prefix).append("_");
        for (int i = 0; i < randomLength; i++) {
            int index = RandomUtils.nextInt(0, CHAR_TEMP.length - 1);
            builder.append(CHAR_TEMP[index]);
        }
        builder.append(LocalDateTime.now().format(FORMATTER));
        return builder.toString();
    }

}