package com.wuyiccc.tianxuan.resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

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


    @Test
    public void existNode() throws InterruptedException, KeeperException {

        Stat stat1 = zooKeeper.exists("/test/java", false);

        if (Objects.isNull(stat1)) {
            log.info("/test/java不存在");
        }

    }

    @Test
    public void getNodeData() throws InterruptedException, KeeperException {

        byte[] data = zooKeeper.getData("/test/java", false, null);
        String content = new String(data);

        log.info(content);
    }

    @Test
    public void getChildNodes() throws InterruptedException, KeeperException {
        List<String> children = zooKeeper.getChildren("/test", false);

        for (String c : children) {
            log.info(c);
        }

    }

    @Test
    public void updateNodeData() throws Exception {

        Stat javaStat = zooKeeper.exists("/test/java", false);

        // 版本号用于控制乐观锁，设置不同，则报错
        zooKeeper.setData("/test/java", "xyz".getBytes(), javaStat.getVersion());

        // 重新获得
        byte[] data = zooKeeper.getData("/test/java", false, null);
        String content = new String(data);

        log.info(content);
    }

    @Test
    public void deleteNode() throws Exception {

        Stat javaStat = zooKeeper.exists("/test/java", false);

        zooKeeper.delete("/test/java", javaStat.getVersion());

    }

}
