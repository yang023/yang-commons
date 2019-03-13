package cn.yang.commons.security.rsa;

import cn.yang.commons.Base64;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * @author yang 2019/3/13
 */
public class Crypto {

    private static Crypto crypto;


    private PublicKey publicKey;
    private PrivateKey privateKey;

    private Crypto(PublicKey publicKey, PrivateKey privateKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public static Crypto getInstance(PublicKey publicKey, PrivateKey privateKey) {
        if (Objects.isNull(crypto)) {
            crypto = new Crypto(publicKey, privateKey);
        }
        return crypto;
    }


    /**
     * 加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    private byte[] encrypt(String str) throws Exception {
        //获取一个加密算法为RSA的加解密器对象cipher。
        Cipher cipher = Cipher.getInstance("RSA");
        //设置为加密模式,并将公钥给cipher。
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        //获得密文
        byte[] secret = cipher.doFinal(str.getBytes());
        //进行Base64编码并返回
        return Base64.encode(secret);
    }

    /**
     * 解密
     *
     * @param secret
     * @return
     * @throws Exception
     */
    private String decrypt(byte[] secret) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        //传递私钥，设置为解密模式。
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //解密器解密由Base64解码后的密文,获得明文字节数组
        byte[] b = cipher.doFinal(Base64.decode(secret));
        //转换成字符串
        return new String(b);
    }
}
