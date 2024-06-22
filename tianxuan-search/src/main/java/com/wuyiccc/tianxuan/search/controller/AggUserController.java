package com.wuyiccc.tianxuan.search.controller;

import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.search.service.AggUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/22 20:15
 */
@RestController
@RequestMapping("/aggUser")
@Slf4j
public class AggUserController {

    @Resource
    private AggUserService aggUserService;

    @PostMapping("/mockData")
    public R<String> mockData() {

        aggUserService.mockData();

        return R.ok();
    }

}
