package com.wuyiccc.tianxuan.resource.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrPool;
import com.wuyiccc.tianxuan.api.config.CuratorConfig;
import com.wuyiccc.tianxuan.api.zookeeper.ZKLock;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.pojo.SysParam;
import com.wuyiccc.tianxuan.pojo.vo.SysParamVO;
import com.wuyiccc.tianxuan.resource.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.zookeeper.data.Stat;
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
    public CommonResult<Integer> modifyMaxResumeRefreshCounts(@RequestParam Integer maxCounts, @RequestParam(required = true) Integer version) throws Exception {


        if (Objects.isNull(maxCounts) || maxCounts < 1) {
            return CommonResult.errorCustom(ResponseStatusEnum.SYSTEM_PARAMS_SETTINGS_ERROR);
        }

        Integer newVersion = sysParamService.modifyMaxResumeRefreshCounts(maxCounts, version);

        return CommonResult.ok(newVersion);
    }

    @PostMapping("/params")
    public CommonResult<SysParamVO> params() throws Exception {

        String path = StrPool.SLASH + BaseInfoProperties.ZK_MAX_RESUME_REFRESH_COUNTS;
        String dataStr = new String(zkClient.getData().forPath(path));

        Integer maxCount = Integer.valueOf(dataStr);

        Stat stat = zkClient.checkExists().forPath(path);

        Integer version = stat.getVersion();

        SysParamVO sysParamVO = new SysParamVO();
        sysParamVO.setMaxResumeRefreshCounts(maxCount);
        sysParamVO.setVersion(version);
        return CommonResult.ok(sysParamVO);
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
