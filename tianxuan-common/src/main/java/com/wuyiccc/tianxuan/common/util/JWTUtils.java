package com.wuyiccc.tianxuan.common.util;

import com.wuyiccc.tianxuan.common.config.JWTConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/7/1 10:26
 */
@Component
@Slf4j
public class JWTUtils {

    @Autowired
    private JWTConfig jwtConfig;

    public static final String AT = "@";


    public String createJWTWithPrefix(String body, Long expireTimes, String prefix) {
        return prefix + AT + createJWT(body, expireTimes);
    }


    public String createJWTWithPrefix(String body, String prefix) {
        return prefix + AT + createJWT(body);
    }

    public String createJWT(String body, Long expireTimes) {
        return dealJWT(body, expireTimes);
    }


    public String createJWT(String body) {
        return dealJWT(body, null);
    }

    private String dealJWT(String body, Long expireTimes) {
        String userKey = jwtConfig.getKey();

        log.info("jwt key: {}", userKey);
        String base64 = new BASE64Encoder().encode(userKey.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(base64.getBytes());
        String jwt = "";
        if (Objects.isNull(expireTimes)) {
            jwt = generateJWT(body, secretKey);
        } else {
            jwt = generateJWT(body, expireTimes, secretKey);
        }

        log.info("jwt: {}", jwt);
        return jwt;
    }

    private String generateJWT(String body, SecretKey secretKey) {
        String jwtToken = Jwts.builder()
                .setSubject(body)
                .signWith(secretKey)
                .compact();
        return jwtToken;
    }


    private String generateJWT(String body, Long expireTimes, SecretKey secretKey) {

        // 定义过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expireTimes);
        String jwtToken = Jwts.builder()
                .setSubject(body)
                .signWith(secretKey)
                .setExpiration(expireDate)
                .compact();
        return jwtToken;
    }



    public String checkJWT(String jwt) {
        String userKey = jwtConfig.getKey();
        log.info("jwt key: {}", userKey);

        String base64 = new BASE64Encoder().encode(userKey.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(base64.getBytes());

        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        // 校验jwt
        Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

        return jws.getBody().getSubject();
    }
}
