package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.enumeration.DealStatusEnum;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import com.wuyiccc.tianxuan.pojo.bo.SearchReportJobBO;
import com.wuyiccc.tianxuan.work.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/2 17:54
 * admin端 report管理
 */
@RestController
@RequestMapping("/adminReport")
@Slf4j
public class AdminReportController {

    @Resource
    private ReportService reportService;


    @PostMapping("/pagedReportRecordList")
    public CommonResult<PagedGridResult> pagedReportRecordList(@RequestBody SearchReportJobBO reportJobBO
            , @RequestParam Integer page
            , @RequestParam Integer pageSize) {


        if (Objects.isNull(page)) page = 0;
        if (Objects.isNull(pageSize)) pageSize = 10;

        LocalDate beginDate = reportJobBO.getBeginDate();
        LocalDate endDate = reportJobBO.getEndDate();



        if (Objects.nonNull(beginDate)) {
            String beginDateTimeStr = LocalDateUtils.format(beginDate, LocalDateUtils.DATE_PATTERN) + " 00:00:00";

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


    @PostMapping("/delete")
    public CommonResult<Integer> delete(@RequestParam String reportId) {

        reportService.updateReportRecordStatus(reportId, DealStatusEnum.DONE);
        return CommonResult.ok();
    }


    @PostMapping("/ignore")
    public CommonResult<Integer> ignore(@RequestParam String reportId) {

        reportService.updateReportRecordStatus(reportId, DealStatusEnum.IGNORE);
        return CommonResult.ok();
    }
}
