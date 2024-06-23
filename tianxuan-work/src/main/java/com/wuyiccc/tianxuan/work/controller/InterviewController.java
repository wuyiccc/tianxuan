package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.Interview;
import com.wuyiccc.tianxuan.pojo.bo.CreateInterviewBO;
import com.wuyiccc.tianxuan.work.service.InterviewService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/23 13:20
 */
@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Resource
    private InterviewService interviewService;


    @PostMapping("/create")
    public R<String> create(@RequestBody CreateInterviewBO createInterviewBO) {

        String id = interviewService.create(createInterviewBO);

        return R.ok(id);
    }

    @PostMapping("/detail")
    public R<Interview> detail(@RequestParam String interviewId, @RequestParam String hrUserId, @RequestParam String companyId) {


        Interview interview = interviewService.detail(interviewId, hrUserId, companyId);
        return R.ok(interview);
    }
}
