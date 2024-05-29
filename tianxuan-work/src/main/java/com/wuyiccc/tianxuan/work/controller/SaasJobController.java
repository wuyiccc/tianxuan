package com.wuyiccc.tianxuan.work.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.bo.SearchJobsBO;
import com.wuyiccc.tianxuan.work.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/5/29 00:35
 */
@RestController
@RequestMapping("/saasJob")
@Slf4j
public class SaasJobController {

    @Resource
    private JobService jobService;


    @PostMapping("/jobList")
    public CommonResult<PagedGridResult> jobList(Integer page, Integer limit) {


        if (Objects.isNull(page)) {

            page = 1;
        }

        if (Objects.isNull(limit)) {

            limit = 10;
        }

        User user = JWTCurrentUserInterceptor.currentUser.get();
        String companyId = user.getHrInWhichCompanyId();

        PagedGridResult gridResult = jobService.queryJobList(null, companyId, page, limit, null);

        return CommonResult.ok(gridResult);
    }


    @PostMapping("/jobDetail")
    public CommonResult<Job> jobDetail(@RequestParam String jobId) {

        if (CharSequenceUtil.isBlank(jobId)) {
            return CommonResult.ok();
        }

        Job job = jobService.queryJobDetail(null, null, jobId);

        return CommonResult.ok(job);
    }



}
