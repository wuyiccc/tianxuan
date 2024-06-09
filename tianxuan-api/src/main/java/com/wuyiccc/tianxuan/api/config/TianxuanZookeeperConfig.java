package com.wuyiccc.tianxuan.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2024/6/8 19:33
 */
@Component
@Data
@ConfigurationProperties("tianxuan.zookeeper")
public class TianxuanZookeeperConfig {


    private String host;

    /**
     * 链接超时时间 ms
     */
    private Integer connectionTimeout;

    /**
     * 会话超时时间 ms
     */
    private Integer sessionTimeout;

    /**
     * 每次重试的间隔时间 ms
     */
    private Integer sleepMsBetweenRetry;

    /**
     * 最大重试次数 ms
     */
    private Integer maxRetries;


    /**
     * 命名空间 ms
     */
    private String namespace;


}
