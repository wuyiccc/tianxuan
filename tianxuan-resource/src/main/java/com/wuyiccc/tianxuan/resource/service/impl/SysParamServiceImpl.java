package com.wuyiccc.tianxuan.resource.service.impl;

import cn.hutool.core.text.StrPool;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.pojo.SysParam;
import com.wuyiccc.tianxuan.resource.mapper.SysParamMapper;
import com.wuyiccc.tianxuan.resource.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

/**
 * @author wuyiccc
 * @date 2024/5/25 23:29
 */
@Slf4j
@Service
public class SysParamServiceImpl implements SysParamService {


    @Resource
    private SysParamMapper sysParamMapper;


    @Resource
    private CuratorFramework zkClient;

    @Resource
    private RedisUtils redisUtils;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer modifyMaxResumeRefreshCounts(Integer maxCounts, Integer version) throws Exception {

        SysParam sysParam = new SysParam();
        sysParam.setId(1001);
        sysParam.setMaxResumeRefreshCounts(maxCounts);
        int res = sysParamMapper.updateById(sysParam);
        if (res != 1) {
            throw new CustomException("更新失败");
        }

        // 乐观锁校验
        String path = StrPool.SLASH + BaseInfoProperties.ZK_MAX_RESUME_REFRESH_COUNTS;

        Stat stat = zkClient.setData()
                .withVersion(version)
                .forPath(path, maxCounts.toString().getBytes(StandardCharsets.UTF_8));

        // 更新到缓存中
        redisUtils.set(BaseInfoProperties.REDIS_MAX_RESUME_REFRESH_COUNTS, String.valueOf(maxCounts));
        return stat.getVersion();
    }



    @Override
    public SysParam getSysParam() {

        return sysParamMapper.selectById(1001);
    }
}
