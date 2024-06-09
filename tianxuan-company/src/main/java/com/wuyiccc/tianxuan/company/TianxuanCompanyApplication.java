package com.wuyiccc.tianxuan.company;

import com.wuyiccc.tianxuan.api.config.CuratorConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wuyiccc
 * @date 2023/6/19 23:02
 */
@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@EnableFeignClients("com.wuyiccc.tianxuan.api.feign")
@MapperScan(basePackages = "com.wuyiccc.tianxuan.company.mapper")
@ComponentScan(basePackages = "com.wuyiccc.tianxuan", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CuratorConfig.class})})
public class TianxuanCompanyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TianxuanCompanyApplication.class, args);
    }
}
