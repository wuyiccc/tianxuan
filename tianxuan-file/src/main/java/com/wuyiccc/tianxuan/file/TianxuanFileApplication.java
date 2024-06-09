package com.wuyiccc.tianxuan.file;

import com.wuyiccc.tianxuan.api.config.RedissonConfig;
import com.wuyiccc.tianxuan.api.zookeeper.ZKConnector;
import io.seata.spring.boot.autoconfigure.SeataAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author wuyiccc
 * @date 2023/6/19 23:01
 */
@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        SeataAutoConfiguration.class
})
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.wuyiccc.tianxuan", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {RedissonConfig.class, ZKConnector.class})})
public class TianxuanFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(TianxuanFileApplication.class, args);
    }
}
