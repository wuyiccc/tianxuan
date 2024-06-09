package com.wuyiccc.tianxuan.work;

import com.wuyiccc.tianxuan.api.zookeeper.ZKConnector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wuyiccc
 * @date 2023/12/18 20:31
 */
@SpringBootApplication
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@MapperScan(basePackages = "com.wuyiccc.tianxuan.work.mapper")
@ComponentScan(basePackages = "com.wuyiccc.tianxuan", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {ZKConnector.class})})
@EnableFeignClients("com.wuyiccc.tianxuan.api.feign")
public class TianxuanWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TianxuanWorkApplication.class, args);
    }
}
