package com.wuyiccc.tianxuan.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wuyiccc
 * @date 2023/6/24 10:12
 */
// 排除数据源的自动装配
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.wuyiccc.tianxuan")
public class TianxuanGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TianxuanGatewayApplication.class, args);
    }
}
