package com.wuyiccc.tianxuan.api.config;

import com.wuyiccc.tianxuan.api.zookeeper.ZKLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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


        // 注册事件
        add("/xyz", client);

        return client;
    }

    private void add(String path, CuratorFramework client) {


        CuratorCache curatorCache = CuratorCache.build(client, path);
        curatorCache.listenable().addListener(new CuratorCacheListener() {
            @Override
            public void event(Type type, ChildData oldData, ChildData data) {
                // type 当前监听到的事件类型
                // oldData 节点更新前的数据, 状态
                // data 节点更新后的数据和状态
                switch (type.name()) {
                    case "NODE_CREATED":
                        log.info("节点创建");
                        break;
                    case "NODE_CHANGED":
                        log.info("节点数据变更");
                        break;
                    case "NODE_DELETED":
                        log.info("节点数据删除");
                        break;
                }



            }
        });
        curatorCache.start();
    }

}
