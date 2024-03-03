package com.wuyiccc.tianxuan.user.controller;

import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/3/3 20:55
 */
@RestController
@RequestMapping("/saasUserInfo")
@Slf4j
public class SaasUserInfoController {


    @Resource
    private UserService userService;


    @PostMapping("/hrList")
    public CommonResult<PagedGridResult> hrList(Integer page, Integer limit) {


        User user = JWTCurrentUserInterceptor.currentUser.get();

        String companyId = user.getHrInWhichCompanyId();

        PagedGridResult res = userService.getHRList(companyId, page, limit);

        return CommonResult.ok(res);
    }


}
