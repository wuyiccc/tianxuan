package com.wuyiccc.tianxuan.user;

import com.wuyiccc.tianxuan.api.config.RedissonConfig;
import com.wuyiccc.tianxuan.api.config.CuratorConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wuyiccc
 * @date 2023/6/19 23:01
 */
@SpringBootApplication(
        exclude = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class
        }
)
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@MapperScan(basePackages = "com.wuyiccc.tianxuan.user.mapper")
@ComponentScan(basePackages = "com.wuyiccc.tianxuan", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {RedissonConfig.class, CuratorConfig.class})})
public class TianxuanUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(TianxuanUserApplication.class, args);
    }
}
