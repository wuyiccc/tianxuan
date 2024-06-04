package com.wuyiccc.tianxuan.resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author wuyiccc
 * @date 2024/6/5 00:16
 */
@Slf4j
public class ZKTest {

    @Test
    public void initZKTest() throws IOException {

        ZooKeeper zk = new ZooKeeper("wuji.local.wuyiccc.com:12191"
                , 5 * 1000
                , null);

        log.info("客户端开始连接zookeeper服务器...");
        log.info("连接状态: {}", zk.getState());

    }
}
