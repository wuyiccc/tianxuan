package com.wuyiccc.tianxuan.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wuyiccc
 * @date 2023/6/24 21:18
 */
@SpringBootApplication
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@MapperScan(basePackages = "com.wuyiccc.tianxuan.search.mapper")
@ComponentScan(basePackages = "com.wuyiccc.tianxuan")
public class TianxuanSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TianxuanSearchApplication.class, args);
    }
}
