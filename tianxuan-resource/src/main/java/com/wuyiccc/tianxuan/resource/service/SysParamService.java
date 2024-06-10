package com.wuyiccc.tianxuan.resource.service;

import com.wuyiccc.tianxuan.pojo.SysParam;

/**
 * @author wuyiccc
 * @date 2024/5/25 23:29
 */
public interface SysParamService {
    Integer modifyMaxResumeRefreshCounts(Integer maxCounts, Integer version) throws Exception;

    SysParam getSysParam();
}
