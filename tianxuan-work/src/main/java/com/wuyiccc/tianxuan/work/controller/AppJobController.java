package com.wuyiccc.tianxuan.work.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.enumeration.JobStatusEnum;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
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
    public R<String> modify(@RequestBody @Valid EditJobBO editJobBO) {

        jobService.modifyJobDetail(editJobBO);

        return R.ok();
    }

    @PostMapping("hr/jobList")
    public R<PagedGridResult> jobListHR(String hrId,
                                        String companyId,
                                        Integer page,
                                        Integer limit,
                                        Integer status) {

        if (StringUtils.isBlank(hrId)) {
            return R.errorMsg("hrId 不能为空");
        }

        if (page == null) page = 1;
        if (limit == null) limit = 10;

        PagedGridResult gridResult = jobService.queryJobList(hrId,
                companyId,
                page,
                limit,
                status);

        return R.ok(gridResult);
    }


    @PostMapping("/hr/jobDetail")
    public R<Job> jobDetail(
            @RequestParam String hrId,
            @RequestParam String companyId,
            @RequestParam String jobId
    ) {

        if (CharSequenceUtil.isBlank(hrId) || CharSequenceUtil.isBlank(companyId) || CharSequenceUtil.isBlank(jobId)) {

            return R.error();
        }


        Job job = jobService.queryJobDetail(hrId, companyId, jobId);


        return R.ok(job);
    }

    @PostMapping("close")
    public R<String> close(
            @RequestParam String hrId
            , @RequestParam String companyId
            , @RequestParam String jobId

    ) {

        if (CharSequenceUtil.isBlank(hrId) || CharSequenceUtil.isBlank(companyId) || CharSequenceUtil.isBlank(jobId)) {
            return R.error();
        }

        jobService.modifyStatus(hrId, companyId, jobId, JobStatusEnum.CLOSE.code);
        return R.ok();
    }

    @PostMapping("open")
    public R<String> open(
            @RequestParam String hrId
            , @RequestParam String companyId
            , @RequestParam String jobId

    ) {

        if (CharSequenceUtil.isBlank(hrId) || CharSequenceUtil.isBlank(companyId) || CharSequenceUtil.isBlank(jobId)) {
            return R.error();
        }

        jobService.modifyStatus(hrId, companyId, jobId, JobStatusEnum.OPEN.code);
        return R.ok();
    }


    @PostMapping("/searchJobs")
    public R<PagedGridResult> searchJobs(@RequestBody SearchJobsBO searchJobsBO
            , Integer page
            , Integer limit) {

        if (Objects.isNull(page)) {
            page = 1;
        }
        if (Objects.isNull(limit)) {
            limit = 10;
        }

        PagedGridResult gridResult = jobService.searchJobs(searchJobsBO, page, limit);
        return R.ok(gridResult);
    }

    @PostMapping("/hr/jobCounts")
    public R<Long> jobCounts(@RequestParam String hrId) {

        Long count = jobService.countJob(hrId);
        return R.ok(count);
    }

    @PostMapping("/getCollectJobCount")
    public R<Long> getCollectJobCount(@RequestParam String candUserId) {

        Long count = jobService.getCollecJobCount(candUserId);
        return R.ok(count);
    }

}
