package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.enumeration.InterviewStatusEnum;
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

    @PostMapping("/cancel")
    public R<String> cancel(@RequestParam String interviewId) {


        interviewService.updateStatus(interviewId, InterviewStatusEnum.CANCEL);
        return R.ok();
    }

    @PostMapping("/accept")
    public R<String> accept(@RequestParam String interviewId) {


        interviewService.updateStatus(interviewId, InterviewStatusEnum.ACCEPT);

        return R.ok();
    }


    @PostMapping("/refuse")
    public R<String> refuse(@RequestParam String interviewId) {

        interviewService.updateStatus(interviewId, InterviewStatusEnum.REFUSE);

        return R.ok();
    }

    @PostMapping("/getHrInterviewRecordCount")
    public R<Long> getHrInterviewRecordCount(@RequestParam String hrId) {

        Long count = interviewService.getHrInterviewRecordCount(hrId);
        return R.ok(count);
    }

    @PostMapping("/getCandInterviewRecordCount")
    public R<Long> getCandInterviewRecordCount(@RequestParam String candUserId) {

        Long count = interviewService.getCandInterviewRecordCount(candUserId);
        return R.ok(count);
    }

}
