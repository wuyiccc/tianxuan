package com.wuyiccc.tianxuan.resource.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wuyiccc.tianxuan.api.zookeeper.ZKConnector;
import com.wuyiccc.tianxuan.api.zookeeper.ZKLock;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.pojo.SysParam;
import com.wuyiccc.tianxuan.pojo.vo.SysParamVO;
import com.wuyiccc.tianxuan.resource.service.SysParamService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/5/25 23:25
 * admin端
 */
@RestController
@RequestMapping("/sysParam")
public class SysParamsController {

    @Resource
    private SysParamService sysParamService;

    @Resource
    private ZKConnector zkConnector;


    @PostMapping("/modifyMaxResumeRefreshCounts")
    public CommonResult<Integer> modifyMaxResumeRefreshCounts(@RequestParam Integer maxCounts, @RequestParam(required = false) Integer version) {

        ZKLock zKLock = zkConnector.getLock("wuyiccc-lock");
        zKLock.getLock();

        if (Objects.isNull(maxCounts) || maxCounts < 1) {
            return CommonResult.errorCustom(ResponseStatusEnum.SYSTEM_PARAMS_SETTINGS_ERROR);
        }

        sysParamService.modifyMaxResumeRefreshCounts(maxCounts, version);

        zKLock.releaseLock();
        return CommonResult.ok(0);
    }

    @PostMapping("/params")
    public CommonResult<SysParamVO> params() {



        SysParam sysParam = sysParamService.getSysParam();

        SysParamVO vo = BeanUtil.copyProperties(sysParam, SysParamVO.class);

        return CommonResult.ok(vo);
    }

}
