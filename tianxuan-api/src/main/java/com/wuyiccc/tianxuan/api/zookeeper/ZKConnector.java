package com.wuyiccc.tianxuan.api.zookeeper;

import com.wuyiccc.tianxuan.api.config.TianxuanZookeeperConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/8 19:34
 */
@Slf4j
@Component
public class ZKConnector {

    @Resource
    private TianxuanZookeeperConfig tianxuanZookeeperConfig;

    private ZooKeeper zooKeeper;

    @PostConstruct
    public void init() {
        try {
            zooKeeper = new ZooKeeper(tianxuanZookeeperConfig.getHost(), tianxuanZookeeperConfig.getTimeout(), null);
        } catch (IOException e) {
            log.error("zk初始化失败", e);
        }
    }


    /**
     * 项目停止，springboot停止之前，调用
     */
    @PreDestroy
    public void close() {
        // 关闭zookeeper连接
        if (Objects.nonNull(zooKeeper)) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                log.error("zk连接关闭失败", e);
            }
        }
    }

    /**
     * 获得锁
     */
    public ZKLock getLock(String lockName) {
        return new ZKLock(zooKeeper, lockName);
    }
}
