package com.wuyiccc.tianxuan.resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author wuyiccc
 * @date 2024/6/8 15:38
 */
@Slf4j
public class ZkWatchTest {


    public static ZooKeeper zooKeeper = null;

    public CountDownLatch countDownLatch = new CountDownLatch(1);

    @BeforeAll
    public static void initZKTest() throws IOException {

        zooKeeper = new ZooKeeper("wuji.local.wuyiccc.com:12191"
                , 5 * 1000
                , null);

    }

    @Test
    public void getChildNodes() throws InterruptedException, KeeperException {

        List<String> children = zooKeeper.getChildren("/test", event -> {
            Watcher.Event.EventType eventType = event.getType();

            String path = event.getPath();

            log.info("监听到路径为: {} 的事件类型为: {}", path, eventType.toString());
        });

        countDownLatch.await();
    }

    @Test
    public void getNodeData() throws InterruptedException, KeeperException {

        byte[] data = zooKeeper.getData("/test", event -> {
            Watcher.Event.EventType eventType = event.getType();
            String path = event.getPath();
            log.info("监听到路径为: {} 的事件类型为: {}", path, eventType);
        }, null);
        countDownLatch.await();
    }

}
