package com.wuyiccc.tianxuan.gateway.controller;

import com.wuyiccc.tianxuan.common.util.DingDingMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2023/6/19 22:59
 */
@RestController
@RequestMapping("/hello")
public class HelloController {



    @Autowired
    private DingDingMsgUtils dingDingMsgUtils;
    @GetMapping("/hello")
    public Object hello() throws Exception {
        return "hello gateway";
    }
}
