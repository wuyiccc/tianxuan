package com.wuyiccc.tianxuan.auth;

import com.google.gson.Gson;
import com.wuyiccc.tianxuan.pojo.test.Stu;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author wuyiccc
 * @date 2023/6/29 23:41
 */
@SpringBootTest
@Slf4j
public class TianxuanApplicationTest {


    private static final String USER_KEY = "qwheoqweuo12oi3h1ohdakj";

    @Test
    public void createJWT() {
        // 1. 对秘钥进行base64编码
        String base64 = new BASE64Encoder().encode(USER_KEY.getBytes());
        // 2. 对base64生成一个秘钥的对象
        SecretKey secretKey = Keys.hmacShaKeyFor(base64.getBytes());
        // 3. jwt生成token字符串
        Stu stu = new Stu("1001", "wuyiccc", 18);
        String stuJson = new Gson().toJson(stu);
        String myJWT = Jwts.builder()
                .setSubject(stuJson) // 设置用户自定义数据
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(secretKey) // 使用哪个秘钥对象进行jwt对象生成
                .compact(); // 压缩并且生成jwt
        log.info("myJWT: {}", myJWT);
    }


    @Test
    public void checkJWT() {


        String myJWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiMTAwMVwiLFwibmFtZVwiOlwid3V5aWNjY1wiLFwiYWdlXCI6MTh9IiwiZXhwIjoxNjg4MTk2ODU5fQ.QGH9jGiIAsSkkbAku5h4ur28kSHfPDuBZRNpFiLbOwk";

        String base64 = new BASE64Encoder().encode(USER_KEY.getBytes());

        SecretKey secretKey = Keys.hmacShaKeyFor(base64.getBytes());

        // 构造解析器
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();

        // 解析jwt
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(myJWT);

        String stuJson = claimsJws.getBody().getSubject();
        Stu stu = new Gson().fromJson(stuJson, Stu.class);

        log.info("stu: {}", stu);

    }

}
