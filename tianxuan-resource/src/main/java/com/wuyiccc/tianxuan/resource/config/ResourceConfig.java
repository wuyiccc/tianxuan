package com.wuyiccc.tianxuan.resource.config;

import cn.hutool.core.text.StrPool;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.pojo.SysParam;
import com.wuyiccc.tianxuan.resource.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/10 09:17
 */
@Slf4j
@Component
public class ResourceConfig implements CommandLineRunner {


    @Resource
    private SysParamService sysParamService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private CuratorFramework zkClient;


    @Override
    public void run(String... args) throws Exception {


        SysParam sysParam = sysParamService.getSysParam();
        if (Objects.isNull(sysParam)) {
            throw new CustomException("加载系统参数失败");
        }

        dealMaxResumeRefreshCount(sysParam.getMaxResumeRefreshCounts());

    }


    private void dealMaxResumeRefreshCount(int count) {

        // 缓存预热数据到redis中
        redisUtils.set(BaseInfoProperties.REDIS_MAX_RESUME_REFRESH_COUNTS, String.valueOf(count));

        // 数据预热到zk中
        String path = StrPool.SLASH + BaseInfoProperties.ZK_MAX_RESUME_REFRESH_COUNTS;

        String data = String.valueOf(count);


        try {
            Stat stat = zkClient.checkExists().forPath(path);
            if (Objects.isNull(stat)) {
                // 节点不存在则创建
                zkClient.create()
                        .creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(path, data.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            log.info("数据缓存预热失败", e);
        }

    }
}
