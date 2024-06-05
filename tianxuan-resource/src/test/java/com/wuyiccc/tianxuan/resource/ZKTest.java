package com.wuyiccc.tianxuan.resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author wuyiccc
 * @date 2024/6/5 00:16
 */
@Slf4j
public class ZKTest {

    public static ZooKeeper zooKeeper = null;


    @BeforeAll
    public static void initZKTest() throws IOException {

        zooKeeper = new ZooKeeper("wuji.local.wuyiccc.com:12191"
                , 5 * 1000
                , null);

    }

    @Test
    public void createNode() throws InterruptedException, KeeperException {

        zooKeeper.create("/test/java", "abc".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
}
