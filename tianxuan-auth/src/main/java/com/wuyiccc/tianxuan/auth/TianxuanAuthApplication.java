package com.wuyiccc.tianxuan.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wuyiccc
 * @date 2023/6/24 21:18
 */
@EnableAsync
@EnableRetry
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@EnableFeignClients("com.wuyiccc.tianxuan.api.feign")
@MapperScan(basePackages = "com.wuyiccc.tianxuan.auth.mapper")
@ComponentScan(basePackages = "com.wuyiccc.tianxuan")
@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
public class TianxuanAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TianxuanAuthApplication.class, args);
    }
}
