package com.wuyiccc.tianxuan.file;

import io.seata.spring.boot.autoconfigure.SeataAutoConfiguration;
import io.seata.spring.boot.autoconfigure.SeataCoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wuyiccc
 * @date 2023/6/19 23:01
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SeataAutoConfiguration.class})
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.wuyiccc.tianxuan")
public class TianxuanFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(TianxuanFileApplication.class, args);
    }
}
