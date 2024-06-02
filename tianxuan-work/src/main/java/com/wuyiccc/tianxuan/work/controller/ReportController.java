package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import com.wuyiccc.tianxuan.pojo.bo.SearchReportJobBO;
import com.wuyiccc.tianxuan.pojo.mo.ReportMO;
import com.wuyiccc.tianxuan.work.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/1 10:47
 */
@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @Resource
    private ReportService reportService;


    @PostMapping("/create")
    public CommonResult<String> create(@RequestBody @Valid ReportMO reportMO) {

        boolean isExist = reportService.isReportRecordExist(reportMO.getReportUserId(), reportMO.getJobId());

        if (isExist) {
            return CommonResult.errorCustom(ResponseStatusEnum.REPORT_RECORD_EXIST_ERROR);
        }

        reportService.saveReportRecord(reportMO);

        return CommonResult.ok();
    }



}
