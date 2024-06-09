package com.wuyiccc.tianxuan.api.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author wuyiccc
 * @date 2024/6/8 19:34
 */
@Slf4j
@Component
public class CuratorConfig {

    @Resource
    private TianxuanZookeeperConfig tianxuanZookeeperConfig;


    @Bean("curatorClient")
    public CuratorFramework curatorClient() {

        // 每间隔x秒重连n次
        //RetryPolicy retryPolicy = new RetryNTimes(tianxuanZookeeperConfig.getMaxRetries(), tianxuanZookeeperConfig.getSleepMsBetweenRetry());

        // baseSleepTimeMs 基准时间, 随着重试的次数累计间隔时间, 第一次重试时间是baseSleepTimeMS
        RetryPolicy backoffRetry = new ExponentialBackoffRetry(tianxuanZookeeperConfig.getSleepMsBetweenRetry(), tianxuanZookeeperConfig.getMaxRetries());

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(tianxuanZookeeperConfig.getHost())
                .connectionTimeoutMs(tianxuanZookeeperConfig.getConnectionTimeout())
                .sessionTimeoutMs(tianxuanZookeeperConfig.getSessionTimeout())
                .retryPolicy(backoffRetry)
                .namespace(tianxuanZookeeperConfig.getNamespace())
                .build();

        // 启动curator客户端
        client.start();

        return client;
    }
}
