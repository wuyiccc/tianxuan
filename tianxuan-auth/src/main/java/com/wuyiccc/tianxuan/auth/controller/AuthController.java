package com.wuyiccc.tianxuan.auth.controller;

import com.wuyiccc.tianxuan.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public R<String> hello() {
        return R.ok("tianxuan-auth: " + port);
    }
}
