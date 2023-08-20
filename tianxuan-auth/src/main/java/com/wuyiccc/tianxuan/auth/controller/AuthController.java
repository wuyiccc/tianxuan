package com.wuyiccc.tianxuan.auth.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2023/6/24 21:22
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @GetMapping("/hello")
    public CommonResult<String> hello() {
        return CommonResult.ok("tianxuan-auth");
    }
}
