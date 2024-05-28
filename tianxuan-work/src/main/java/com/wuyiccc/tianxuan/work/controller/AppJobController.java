package com.wuyiccc.tianxuan.work.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.bo.EditJobBO;
import com.wuyiccc.tianxuan.work.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
}
