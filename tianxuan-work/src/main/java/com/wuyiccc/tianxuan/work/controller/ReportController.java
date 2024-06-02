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


    @PostMapping("/pagedReportRecordList")
    public CommonResult<PagedGridResult> pagedReportRecordList(@RequestBody SearchReportJobBO reportJobBO
    , @RequestParam Integer page
    , @RequestParam Integer pageSize) {


        if (Objects.isNull(page)) page = 0;
        if (Objects.isNull(pageSize)) pageSize = 10;

        LocalDate beginDate = reportJobBO.getBeginDate();
        LocalDate endDate = reportJobBO.getEndDate();



        if (Objects.nonNull(beginDate)) {
            String beginDateTimeStr = LocalDateUtils.format(beginDate, LocalDateUtils.DATE_PATTERN) + "00:00:00";

            LocalDateTime beginDateTime = LocalDateUtils.parseLocalDateTime(beginDateTimeStr, LocalDateUtils.DATETIME_PATTERN);

            reportJobBO.setBeginDateTime(beginDateTime);
        }

        if (Objects.nonNull(endDate)) {

            // 结束日期加上时间: 23:59:59
            String endDateTimeStr = LocalDateUtils.format(endDate,
                    LocalDateUtils.DATE_PATTERN) + " 23:59:59";

            LocalDateTime endDateTime = LocalDateUtils.parseLocalDateTime(endDateTimeStr,
                    LocalDateUtils.DATETIME_PATTERN);
            reportJobBO.setEndDateTime(endDateTime);
        }



        PagedGridResult pagedGridResult = reportService.pagedReportRecordList(reportJobBO, page, pageSize);

        return CommonResult.ok(pagedGridResult);
    }
}
