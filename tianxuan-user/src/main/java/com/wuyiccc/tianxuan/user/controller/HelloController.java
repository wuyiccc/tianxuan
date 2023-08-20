package com.wuyiccc.tianxuan.user.controller;

import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.test.Stu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2023/6/19 22:59
 */
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {



    @Value("${server.port}")
    private String port;


    @GetMapping("/hello")
    public CommonResult<Stu> hello() {

        User user = JWTCurrentUserInterceptor.currentUser.get();
        log.info("user: {}", user);

        Stu stu = new Stu("10001", "zhangsan", 29);

        log.info("lb测试, 当前端口号为: " + port);
        log.debug(stu.toString());
        return CommonResult.ok(stu);
    }
}
