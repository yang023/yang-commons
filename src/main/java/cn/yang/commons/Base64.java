package cn.yang.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author yang 2019/2/2
 */
public class Base64 {

    public static String encode2String(String str) {
        return java.util.Base64.getEncoder().encodeToString(encode(str));
    }

    public static byte[] encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").getBytes();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String decode2String(String str) {
        try {
            return URLDecoder.decode(new String(decode(str)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] decode(String str) {
        return java.util.Base64.getDecoder().decode(str);
    }
}
