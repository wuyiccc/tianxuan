package com.wuyiccc.tianxuan.api.zookeeper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/8 22:20
 */
@Slf4j
public class ZKLock {


    private ZooKeeper zooKeeper;

    // 锁名称
    private String lockName;

    private static final String lockSpace = "/locks";

    private String selfLock = null;


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

            }

        } catch (Exception e) {
            log.error("获取锁失败", e);
        }
    }

    /**
     * 释放锁
     */
    public void releaseLock() {

    }
}
