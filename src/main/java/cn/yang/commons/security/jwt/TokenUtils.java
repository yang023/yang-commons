package cn.yang.commons.security.jwt;

import cn.yang.commons.security.rsa.RSAKeyFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author yang 2019/1/31
 */
public class TokenUtils {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public TokenUtils(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String token(Map<String, Object> claims) {
        long currentTimeMillis = System.currentTimeMillis();
        String subject = JWTContext.JWT_SUBJECT;
        long expiration = JWTContext.JWT_TTL;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + expiration))
                .signWith(getPrivateKey())
                .compact();

    }

    public Claims verify(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token);
        return claimsJws.getBody();
    }

    private Key getPublicKey() {
        return this.publicKey;
    }

    private Key getPrivateKey() {
        return this.privateKey;
    }


    public static void main(String[] args) throws Exception {
        RSAKeyFactory factory = new RSAKeyFactory();
        factory.loadKeyPair("/Users/yang/workspace/demo/demo", "key-pair-store");
        TokenUtils tokenUtils = new TokenUtils(factory.getPrivateKey(), factory.getPublicKey());
        String token = tokenUtils.token(Collections.singletonMap("user", "Yang"));
        Claims verify = tokenUtils.verify(token);
        System.out.println(verify);
    }
}
