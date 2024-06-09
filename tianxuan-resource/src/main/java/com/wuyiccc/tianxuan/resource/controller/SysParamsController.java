package com.wuyiccc.tianxuan.resource.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wuyiccc.tianxuan.api.config.CuratorConfig;
import com.wuyiccc.tianxuan.api.zookeeper.ZKLock;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.pojo.SysParam;
import com.wuyiccc.tianxuan.pojo.vo.SysParamVO;
import com.wuyiccc.tianxuan.resource.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyiccc
 * @date 2024/5/25 23:25
 * admin端
 */
@Slf4j
@RestController
@RequestMapping("/sysParam")
public class SysParamsController {

    @Resource
    private SysParamService sysParamService;


    @Resource
    private CuratorFramework zkClient;

    @PostMapping("/modifyMaxResumeRefreshCounts")
    public CommonResult<Integer> modifyMaxResumeRefreshCounts(@RequestParam Integer maxCounts, @RequestParam(required = false) Integer version) throws Exception {

        // 可重入分布式锁
        //InterProcessMutex processMutex = new InterProcessMutex(zkClient, "/mute_locks");
        InterProcessReadWriteLock processReadWriteLock = new InterProcessReadWriteLock(zkClient, "/rw_locks");
        processReadWriteLock.writeLock().acquire();

        if (Objects.isNull(maxCounts) || maxCounts < 1) {
            return CommonResult.errorCustom(ResponseStatusEnum.SYSTEM_PARAMS_SETTINGS_ERROR);
        }

        try {
            sysParamService.modifyMaxResumeRefreshCounts(maxCounts, version);
        } finally {
            processReadWriteLock.writeLock().release();
        }

        return CommonResult.ok(0);
    }

    @PostMapping("/params")
    public CommonResult<SysParamVO> params() throws Exception {

        //InterProcessReadWriteLock processReadWriteLock = new InterProcessReadWriteLock(zkClient, "/rw_locks");
        InterProcessSemaphoreV2 semaphoreV2 = new InterProcessSemaphoreV2(zkClient, "/sem_locks", 5);

        //processReadWriteLock.readLock().acquire();
        Lease lease = semaphoreV2.acquire();

        try {
            SysParam sysParam = sysParamService.getSysParam();
            SysParamVO vo = BeanUtil.copyProperties(sysParam, SysParamVO.class);
            return CommonResult.ok(vo);
        } finally {
            //processReadWriteLock.readLock().release();
            semaphoreV2.returnLease(lease);
        }
    }


    @PostMapping("/counts")
    public CommonResult<Integer> counts() throws Exception {

        SharedCount sharedCount = new SharedCount(zkClient, "/shared_counts", 88);

        sharedCount.start();

        int value = sharedCount.getCount();
        log.info("当前值: {}", value);
        sharedCount.setCount(99);

        return CommonResult.ok(sharedCount.getCount());
    }

}
