package cn.yang.commons.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.KeyPair;
import java.util.Date;
import java.util.Map;

/**
 * @author yang 2019/1/31
 */
public class TokenUtils {

    private KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);


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
        return keyPair.getPublic();
    }

    private Key getPrivateKey() {
        return keyPair.getPrivate();
    }

}
