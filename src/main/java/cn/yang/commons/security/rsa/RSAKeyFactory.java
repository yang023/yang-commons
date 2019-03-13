package cn.yang.commons.security.rsa;

import cn.yang.commons.Base64;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.List;

/**
 * @author yang 2019/3/13
 */
public class RSAKeyFactory {
    private static final String KEY_ALGORITHM = "RSA";

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void initKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator rsa = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        KeyPair keyPair = rsa.genKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public void store(String path, String fileName) throws IOException {
        File storeFile = new File(path, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(storeFile);
        String publicKeyBase64 = new String(Base64.encode(publicKey.getEncoded()));
        String privateKeyBase64 = new String(Base64.encode(privateKey.getEncoded()));

        List<String> keys = Arrays.asList(publicKeyBase64, privateKeyBase64);
        IOUtils.writeLines(keys, "\n", fileOutputStream, "UTF-8");
    }

    public void loadKeyPair(String path, String fileName) throws IOException, GeneralSecurityException {
        File storeFile = new File(path, fileName);
        FileInputStream fileInputStream = new FileInputStream(storeFile);

        List<String> keys = IOUtils.readLines(fileInputStream, "UTF-8");
        String publicKeyBase64 = keys.get(0);
        String privateKeyBase64 = keys.get(1);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyBase64.getBytes()));
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyBase64.getBytes()));

        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
        privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
        publicKey = factory.generatePublic(x509EncodedKeySpec);
    }
}
