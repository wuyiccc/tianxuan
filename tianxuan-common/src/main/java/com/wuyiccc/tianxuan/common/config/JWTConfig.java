package com.wuyiccc.tianxuan.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2023/7/1 10:20
 */
@Component
@Data
@ConfigurationProperties("tianxuan.jwt.auth")
public class JWTConfig {

    private String key;
}
