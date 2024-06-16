package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.work.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/2 17:19
 */
@RestController
@RequestMapping("/adminJob")
@Slf4j
public class AdminJobController {

    @Resource
    private JobService jobService;

    @PostMapping("/jobDetail")
    public R<Job> jobDetail(
            @RequestParam String jobId
    ) {


        Job job = jobService.queryJobDetail(null, null, jobId);


        return R.ok(job);
    }


}
