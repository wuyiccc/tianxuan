package com.wuyiccc.tianxuan.resource.service.impl;

import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.pojo.SysParam;
import com.wuyiccc.tianxuan.resource.mapper.SysParamMapper;
import com.wuyiccc.tianxuan.resource.service.SysParamService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/5/25 23:29
 */
@Slf4j
@Service
public class SysParamServiceImpl implements SysParamService {


    @Resource
    private SysParamMapper sysParamMapper;

    @Override
    public void modifyMaxResumeRefreshCounts(Integer maxCounts, Integer version) {

        SysParam sysParam = new SysParam();

        sysParam.setId(1001);
        sysParam.setMaxResumeRefreshCounts(maxCounts);

        int res = sysParamMapper.updateById(sysParam);
        if (res != 1) {
            throw new CustomException("更新失败");
        }
    }



    @Override
    public SysParam getSysParam() {

        return sysParamMapper.selectById(1001);
    }
}
