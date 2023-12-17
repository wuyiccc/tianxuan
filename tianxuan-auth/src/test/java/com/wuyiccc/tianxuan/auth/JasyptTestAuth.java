package com.wuyiccc.tianxuan.auth;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.jupiter.api.Test;

/**
 * @author wuyiccc
 * @date 2023/12/17 09:02
 */
@Slf4j
public class JasyptTestAuth extends TianxuanAuthApplicationTest {

    @Test
    void testJasypt() {

        // 实例化加密器
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        // 配置加密算法和秘钥
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        // 用于加密的秘钥(盐), 可以是随机字符串
        config.setPassword("wuyiccc");
        // 设置加密算法, 默认
        config.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setConfig(config);

        // 对密码进行加密
        String myPwd = "wuyiccc123iiizzq123456789";
        // 对密码进行加密 1ofMxvbVoddmk4IuqVPVGQ==
        // wYfaCqlxhfsDo7nLsSdSlA==
        String encryptedPwd = encryptor.encrypt(myPwd);

        log.info("原密码为: {}, 加密之后的密码为: {}", myPwd, encryptedPwd);

        // 解密
        String originalEncryptedPwd = "wYfaCqlxhfsDo7nLsSdSlA==";
        String decrypt = encryptor.decrypt(originalEncryptedPwd);
        log.info("解密之后的密码: {}", decrypt);
    }
}
