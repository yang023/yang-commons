package cn.yang.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author yang 2019/2/2
 */
public class Base64 {

    public static byte[] encode(byte[] source) {
        return java.util.Base64.getEncoder().encode(source);
    }

    public static byte[] encode(String source) {
        return encode(source.getBytes());
    }

    public static byte[] decode(byte[] decrypt) {
        return java.util.Base64.getDecoder().decode(decrypt);
    }

    public static byte[] decode(String decrypt) {
        return decode(decrypt.getBytes());
    }
}
