package cn.yang.commons.wx.crpty;

import cn.yang.commons.Base64;
import cn.yang.commons.DataConverter;
import com.google.gson.Gson;

import java.util.Objects;

public class WXCore {

    /**
     * 解密数据
     *
     * @return
     */
    public static DataConverter decrypt(String encryptedData, String sessionKey, String iv) {
        String result = null;
        AES aes = new AES();
        byte[] resultByte = aes.decrypt(
                Base64.decode(encryptedData),
                Base64.decode(sessionKey),
                Base64.decode(iv));
        if (!Objects.isNull(resultByte) && resultByte.length > 0) {
            result = new String(WxPKCS7Encoder.decode(resultByte));
        }
        return new DataConverter(result, new Gson());
    }
}