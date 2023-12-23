package com.wuyiccc.tianxuan.file.controller;

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

    @GetMapping("/hello")
    public Object hello() {

        return "hello tianxuan-company";
    }
}
