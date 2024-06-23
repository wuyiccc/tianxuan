package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.bo.CreateInterviewBO;
import com.wuyiccc.tianxuan.work.service.InterviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
