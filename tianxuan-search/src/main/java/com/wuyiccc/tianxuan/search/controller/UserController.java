package com.wuyiccc.tianxuan.search.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.search.pojo.User;
import com.wuyiccc.tianxuan.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2023/9/2 19:19
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserById")
    public CommonResult<User> getUserById(@RequestParam Long id) {
        User user = userService.getUserById(id);
        return CommonResult.ok(user);
    }

}
