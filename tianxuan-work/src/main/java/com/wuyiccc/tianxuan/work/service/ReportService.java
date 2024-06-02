package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.common.enumeration.DealStatusEnum;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.bo.SearchReportJobBO;
import com.wuyiccc.tianxuan.pojo.mo.ReportMO;

/**
 * @author wuyiccc
 * @date 2024/6/1 12:11
 */
public interface ReportService {
    boolean isReportRecordExist(String reportUserId, String jobId);

    void saveReportRecord(ReportMO reportMO);

    PagedGridResult pagedReportRecordList(SearchReportJobBO reportJobBO, Integer page, Integer pageSize);

    void updateReportRecordStatus(String reportId, DealStatusEnum dealStatusEnum);
}
