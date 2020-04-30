package cn.yang.commons;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Arrays;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author yang 2019/4/16
 */
@Slf4j
public class BasicUtil {


    /**
     * 生成uuid
     *
     * @return ${8}-${8}-${8}-${8} 标准形式uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成32位无连接线uuid
     *
     * @return 32位uuid字符串
     */
    public static String uuid32() {
        return uuid().replaceAll("-", "");
    }

    /**
     * 位或运算
     * 如 1 | 2 | 4 = 7
     *
     * @param num 待处理的十进制数
     * @return 位或运算结果
     */
    public static long binaryAdd(List<Long> num) {
        return num.stream().mapToLong(item -> 1 << item).reduce(0L, (a, b) -> a | b);
    }

    /**
     * 分解位或运算结果，并返回其十进制结果
     * 如 1 | 2 | 4 = 7
     * 分解结果 binarySplit(7) = [1, 2, 4]
     *
     * @param roleExp 待处理的十进制数
     * @return 分解位或运算结果的十进制整数集合
     */
    public static List<Integer> binarySplit(long roleExp) {
        String binaryString = Long.toBinaryString(roleExp);
        int[] array = Stream.of(binaryString.split("")).mapToInt(Integer::valueOf).toArray();
        int[] reverse = Arrays.reverse(array);
        ArrayList<Integer> result = Lists.newArrayListWithCapacity(array.length);
        for (int i = reverse.length - 1; i >= 0; i--) {
            if (reverse[i] != 0) {
                result.add(i);
            }
        }
        return result;
    }

    public static Charset utf8() {
        return Charset.forName("UTF-8");
    }
}
