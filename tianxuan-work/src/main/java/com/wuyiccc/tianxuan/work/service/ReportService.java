package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.pojo.mo.ReportMO;

/**
 * @author wuyiccc
 * @date 2024/6/1 12:11
 */
public interface ReportService {
    boolean isReportRecordExist(String reportUserId, String jobId);

    void saveReportRecord(ReportMO reportMO);
}
