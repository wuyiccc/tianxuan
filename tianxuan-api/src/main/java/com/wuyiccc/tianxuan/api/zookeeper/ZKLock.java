package com.wuyiccc.tianxuan.api.zookeeper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @author wuyiccc
 * @date 2024/6/8 22:20
 * 仅为demo代码, 有并发问题
 */
@Slf4j
public class ZKLock {


    private ZooKeeper zooKeeper;

    // 锁名称
    private String lockName;

    private static final String lockSpace = "/locks";

    private String selfLock = null;

    private CountDownLatch waitToGetLatch = new CountDownLatch(1);


    public ZKLock(ZooKeeper zooKeeper, String lockName) {
        this.zooKeeper = zooKeeper;
        this.lockName = lockName;

        /**
         * 创建的同事初始化分布式锁的命名空间 /locks
         * 所有的分布式锁都在 /lockSpace下面
         */
        try {
            Stat stat = zooKeeper.exists(lockSpace, false);
            if (Objects.isNull(stat)) {
                // 路径不存在, 则创建节点
                zooKeeper.create(lockSpace, ZKLock.class.getName().getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            log.error("创建锁命名空间失败", e);
        }


    }

    /**
     * 获得锁
     */
    public void getLock() {

        if (CharSequenceUtil.isBlank(lockName)) {
            throw new CustomException("锁名称不能为空");
        }

        // 创建锁 创建临时有序的节点 lockName-0000001
        try {
            selfLock = zooKeeper.create(lockSpace + CharPool.SLASH + lockName
                    , null
                    , ZooDefs.Ids.OPEN_ACL_UNSAFE
                    , CreateMode.EPHEMERAL_SEQUENTIAL);
            log.info("{} 创建锁完毕", selfLock);

            List<String> subLocks = zooKeeper.getChildren(lockSpace, false);
            if (CollUtil.isEmpty(subLocks) || Objects.isNull(selfLock)) {
                throw new CustomException("创建锁失败, subLocks为空, 或者selfLock为空");
            }

            if (subLocks.size() == 1) {
                // 如果子节点的数量只有一个, 那么当前节点的顺序是最小的有序值, 直接获得分布式锁
                log.info("成功获取到锁");
                return;
            } else {

                // 对所有子节点进行排序
                subLocks.sort(Comparator.naturalOrder());

                // /locks/lockname00001
                // 获取到lockname00001
                String shortSelfLock = StringUtils.substringAfterLast(selfLock, CharPool.SLASH);
                // 获取当前节点所处位置的下标
                int selfIndex = Collections.binarySearch(subLocks, shortSelfLock);


                if (selfIndex == -1) {
                    // 说明zk数据服务出现问题
                    throw new CustomException("zk server异常");
                } else if (selfIndex > 0) {
                    // 说明当前节点不是第一个节点, 监听上一个节点
                    String preLock = subLocks.get(selfIndex - 1);
                    // 监听上一个节点
                    // TODO(wuyiccc): 可能会有并发问题, 监听的时候, 前面一个锁已经被删除了
                    zooKeeper.getData(lockSpace + CharPool.SLASH + preLock, event -> {
                        if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
                            // 如果上一个节点被删除, 则继续尝试获得锁
                            log.info("监听到前面的锁: {} 已被释放, 本客户端: {} 成功获得分布式锁", preLock, selfLock);
                            waitToGetLatch.countDown();
                        }
                    }, null);

                    log.info("当前锁没有获取到分布式锁, 开始等待");
                    waitToGetLatch.await();
                    log.info("等待完毕, 已经获取到锁");
                    return;
                } else if (selfIndex == 0) {
                    // 说明当前节点是第一个节点, 获取到锁
                    log.info("成功获得分布式锁, {}", selfLock);
                    return;
                }
            }

        } catch (Exception e) {
            log.error("获取锁失败", e);
        }
    }

    /**
     * 释放锁
     */
    public void releaseLock() {

        try {
            zooKeeper.delete(selfLock, -1);
        } catch (Exception e) {
            log.error("释放锁失败", e);
        }
    }
}
