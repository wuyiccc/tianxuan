package com.wuyiccc.tianxuan.resource.service;

import com.wuyiccc.tianxuan.pojo.SysParam;

/**
 * @author wuyiccc
 * @date 2024/5/25 23:29
 */
public interface SysParamService {
    void modifyMaxResumeRefreshCounts(Integer maxCounts, Integer version);

    SysParam getSysParam();
}
