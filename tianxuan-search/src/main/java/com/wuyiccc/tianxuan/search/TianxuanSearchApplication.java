package com.wuyiccc.tianxuan.search;

import com.wuyiccc.tianxuan.api.config.CuratorConfig;
import com.wuyiccc.tianxuan.api.config.RedissonConfig;
import org.dromara.easyes.starter.register.EsMapperScan;
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
 * @date 2023/6/24 21:18
 */
@SpringBootApplication(
        exclude = {
                MongoDataAutoConfiguration.class,
                MongoAutoConfiguration.class
        }
)
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@MapperScan(basePackages = "com.wuyiccc.tianxuan.search.mapper")
@ComponentScan(basePackages = "com.wuyiccc.tianxuan", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {RedissonConfig.class, CuratorConfig.class})})
@EsMapperScan("com.wuyiccc.tianxuan.search.mapper")
public class TianxuanSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TianxuanSearchApplication.class, args);
    }
}
