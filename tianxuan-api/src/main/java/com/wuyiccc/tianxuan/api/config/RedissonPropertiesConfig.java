package com.wuyiccc.tianxuan.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuyiccc
 * @date 2024/3/10 08:54
 */
@ConfigurationProperties("tianxuan.redisson")
@Data
public class RedissonPropertiesConfig {

    private String host;

    private String port;

    private String password;

    private Integer database;
}
