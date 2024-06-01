package com.wuyiccc.tianxuan.work.service.impl;

import com.wuyiccc.tianxuan.common.enumeration.DealStatusEnum;
import com.wuyiccc.tianxuan.pojo.mo.ReportMO;
import com.wuyiccc.tianxuan.work.repository.ReportRepository;
import com.wuyiccc.tianxuan.work.service.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/1 12:11
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportRepository reportRepository;

    @Override
    public boolean isReportRecordExist(String reportUserId, String jobId) {

        ReportMO reportMO = reportRepository.findByReportUserIdAndJobId(reportUserId, jobId);
        return !Objects.isNull(reportMO);
    }

    @Override
    public void saveReportRecord(ReportMO reportMO) {

        reportMO.setDealStatus(DealStatusEnum.WAITING.type);
        reportMO.setCreatedTime(LocalDateTime.now());
        reportMO.setUpdatedTime(LocalDateTime.now());

        reportRepository.save(reportMO);
    }
}
