package cn.yang.commons.wx.crpty;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class AES {
	
	public static boolean initialized = false;
 
	/**
	 * AES解密
	 * 
	 * @param content
	 *            密文
	 * @return
	 */
	public byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) {
		initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	public static void initialize() {
		if (initialized) {
            return;
        }
		Security.addProvider(new BouncyCastleProvider());
		initialized = true;
	}

	public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		return params;
	}
}