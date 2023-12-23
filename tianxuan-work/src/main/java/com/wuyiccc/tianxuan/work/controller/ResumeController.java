package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.exception.RemoteCallCustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.work.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2023/12/18 20:35
 */
@RestController
@RequestMapping("/resume")
@Slf4j
public class ResumeController {


    @Resource
    private ResumeService resumeService;

    /**
     * 初始化用户简历
     */
    @PostMapping("init")
    public CommonResult<String> init(@RequestParam("userId") String userId) {

        try {
            resumeService.initResume(userId);
        } catch (Exception e) {
            throw new RemoteCallCustomException(e.getMessage());
        }
        return CommonResult.ok();
    }
}
