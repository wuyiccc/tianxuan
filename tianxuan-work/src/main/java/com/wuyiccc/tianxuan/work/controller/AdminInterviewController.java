package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.work.service.InterviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/23 15:18
 */
@RestController
@RequestMapping("/adminInterview")
public class AdminInterviewController {


    @Resource
    private InterviewService interviewService;


    @PostMapping("/page")
    public R<PagedGridResult> page(@RequestParam Integer page, @RequestParam Integer limit) {

        User user = JWTCurrentUserInterceptor.currentUser.get();
        String companyId = user.getHrInWhichCompanyId();

        PagedGridResult pagedGridResult = interviewService.pageSearch(companyId, null, null, page, limit);
        return R.ok(pagedGridResult);
    }


}
