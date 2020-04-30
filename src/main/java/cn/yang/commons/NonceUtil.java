package cn.yang.commons;

import java.util.Random;
import java.util.function.Supplier;

/**
 * @author yang
 * <p>
 * 随机字符串生成工具
 */
public class NonceUtil {

    private static final String UPPER_CASE_LETTER;
    private static final String LOWER_CASE_LETTER;
    private static final String NUMBER;

    private static final Random RANDOM = new Random();

    private static final class RandomUtil {
        public static int nextInt(final int startInclusive, final int endExclusive) {

            if (startInclusive == endExclusive) {
                return startInclusive;
            }

            return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
        }
    }

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            builder.append((char) ('a' + i));
        }
        String s = builder.toString();

        LOWER_CASE_LETTER = s;
        UPPER_CASE_LETTER = s.toUpperCase();
        NUMBER = "1234567890";
    }

    /**
     * 从from字符串中根据action规则获取length个字符串
     *
     * @param length 生成串长度
     * @param from   生成串的字符来源
     * @param action 自定义生成规则
     * @return length长度的字符串
     */
    public static String pickup(int length, String from, Supplier<Integer> action) {
        char[] chars = from.toCharArray();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(chars[action.get()]);
        }

        return builder.toString();
    }

    /**
     * 从from字符串中获取length长度随机字符串
     *
     * @param length 生成串长度
     * @param from   生成串的字符来源
     * @return length长度的字符串
     */
    public static String pickup(int length, String from) {
        return pickup(length, from, defaultAction(from.length()));
    }

    /**
     * 生成length长度随机字符串
     *
     * @param length 生成串长度
     * @return length长度的字符串
     */
    public static String string(int length) {
        return pickup(length, UPPER_CASE_LETTER + LOWER_CASE_LETTER + NUMBER, defaultAction(26 + 26 + 10));
    }

    public static String letters(int length) {
        return pickup(length, UPPER_CASE_LETTER + LOWER_CASE_LETTER, defaultAction(26 + 26));
    }

    /**
     * 生成length长度随机大写字符串
     *
     * @param length 生成串长度
     * @return length长度的字符串
     */
    public static String upperCase(int length) {
        return pickup(length, UPPER_CASE_LETTER, defaultAction(26));
    }

    /**
     * 生成length长度随机小写字符串
     *
     * @param length 生成串长度
     * @return length长度的字符串
     */
    public static String lowerCase(int length) {
        return pickup(length, LOWER_CASE_LETTER, defaultAction(26));
    }

    /**
     * 生成length长度随机数字字符串
     *
     * @param length 生成串长度
     * @return length长度的字符串
     */
    public static String number(int length) {
        return pickup(length, NUMBER, defaultAction(10));
    }

    /**
     * 默认生成规则
     *
     * @param end 随机数最大值
     * @return {@link Supplier}
     */
    private static Supplier<Integer> defaultAction(int end) {
        return () -> RandomUtil.nextInt(0, end);
    }

    /**
     * 字符串转Character数组
     *
     * @param str 源字符串
     * @return Character数组
     */
    public static Character[] toCharacterArray(String str) {
        return toCharacterArray(str.toCharArray());
    }

    /**
     * char数组转Character数组
     *
     * @param chars char数组
     * @return Character数组
     */
    public static Character[] toCharacterArray(char[] chars) {
        int length = chars.length;
        Character[] characters = new Character[length];
        for (int i = 0; i < length; i++) {
            characters[i] = chars[i];
        }
        return characters;
    }

}
