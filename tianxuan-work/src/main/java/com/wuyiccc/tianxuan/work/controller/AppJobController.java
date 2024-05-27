package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.bo.EditJobBO;
import com.wuyiccc.tianxuan.work.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
