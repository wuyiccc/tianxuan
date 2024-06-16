package com.wuyiccc.tianxuan.search.controller;

import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.search.service.ResumeEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/16 11:09
 */
@RestController
@RequestMapping("/resumeEs")
@Slf4j
public class ResumeEsController {

    @Resource
    private ResumeEsService resumeEsService;


    @GetMapping("/test")
    public R<String> test() {

        resumeEsService.insert();
        return R.ok();
    }


}
