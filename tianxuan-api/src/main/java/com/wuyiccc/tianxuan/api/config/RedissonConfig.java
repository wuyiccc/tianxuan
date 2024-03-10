package com.wuyiccc.tianxuan.api.config;

import cn.hutool.core.text.CharPool;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuyiccc
 * @date 2024/3/10 08:15
 */
@Configuration
public class RedissonConfig {




    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {

        RedissonPropertiesConfig properties = redissonPropertiesConfig();

        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + CharPool.COLON + properties.getPort())
                .setPassword(properties.getPassword())
                .setDatabase(properties.getDatabase())
                .setConnectionMinimumIdleSize(10)
                .setConnectionPoolSize(20)
                .setIdleConnectionTimeout(60 * 1000)
                .setConnectTimeout(15 * 1000)
                .setTimeout(15 * 1000);

        return Redisson.create(config);
    }

    @Bean
    public RedissonPropertiesConfig redissonPropertiesConfig() {
        return new RedissonPropertiesConfig();
    }
}
