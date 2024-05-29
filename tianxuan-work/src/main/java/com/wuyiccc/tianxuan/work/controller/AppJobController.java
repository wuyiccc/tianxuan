package com.wuyiccc.tianxuan.work.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.enumeration.JobStatusEnum;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.bo.EditJobBO;
import com.wuyiccc.tianxuan.pojo.bo.SearchJobsBO;
import com.wuyiccc.tianxuan.work.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/5/27 22:31
 */
@RestController
@RequestMapping("/appJob")
@Slf4j
public class AppJobController {


    @Resource
    private JobService jobService;


    @PostMapping("/modify")
    public CommonResult<String> modify(@RequestBody @Valid EditJobBO editJobBO) {

        jobService.modifyJobDetail(editJobBO);

        return CommonResult.ok();
    }

    @PostMapping("hr/jobList")
    public CommonResult<PagedGridResult> jobListHR(String hrId,
                                                   String companyId,
                                                   Integer page,
                                                   Integer limit,
                                                   Integer status) {

        if (StringUtils.isBlank(hrId)) {
            return CommonResult.errorMsg("hrId 不能为空");
        }

        if (page == null) page = 1;
        if (limit == null) limit = 10;

        PagedGridResult gridResult = jobService.queryJobList(hrId,
                companyId,
                page,
                limit,
                status);

        return CommonResult.ok(gridResult);
    }


    @PostMapping("/hr/jobDetail")
    public CommonResult<Job> jobDetail(
            @RequestParam String hrId,
            @RequestParam String companyId,
            @RequestParam String jobId
    ) {

        if (CharSequenceUtil.isBlank(hrId) || CharSequenceUtil.isBlank(companyId) || CharSequenceUtil.isBlank(jobId)) {

            return CommonResult.error();
        }


        Job job = jobService.queryJobDetail(hrId, companyId, jobId);


        return CommonResult.ok(job);
    }

    @PostMapping("close")
    public CommonResult<String> close(
            @RequestParam String hrId
            , @RequestParam String companyId
            , @RequestParam String jobId

    ) {

        if (CharSequenceUtil.isBlank(hrId) || CharSequenceUtil.isBlank(companyId) || CharSequenceUtil.isBlank(jobId)) {
            return CommonResult.error();
        }

        jobService.modifyStatus(hrId, companyId, jobId, JobStatusEnum.CLOSE.code);
        return CommonResult.ok();
    }

    @PostMapping("open")
    public CommonResult<String> open(
            @RequestParam String hrId
            , @RequestParam String companyId
            , @RequestParam String jobId

    ) {

        if (CharSequenceUtil.isBlank(hrId) || CharSequenceUtil.isBlank(companyId) || CharSequenceUtil.isBlank(jobId)) {
            return CommonResult.error();
        }

        jobService.modifyStatus(hrId, companyId, jobId, JobStatusEnum.OPEN.code);
        return CommonResult.ok();
    }


    @PostMapping("/searchJobs")
    public CommonResult<PagedGridResult> searchJobs(@RequestBody SearchJobsBO searchJobsBO
            , Integer page
            , Integer limit) {

        if (Objects.isNull(page)) {
            page = 1;
        }
        if (Objects.isNull(limit)) {
            limit = 10;
        }

        PagedGridResult gridResult = jobService.searchJobs(searchJobsBO, page, limit);
        return CommonResult.ok(gridResult);
    }

}
