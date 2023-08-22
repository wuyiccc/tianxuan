package com.wuyiccc.tianxuan.auth.controller;

import com.google.gson.Gson;
import com.wuyiccc.tianxuan.auth.service.AdminService;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.bo.AdminBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author wuyiccc
 * @date 2023/8/22 22:32
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController extends BaseInfoProperties {


    @Autowired
    private AdminService adminService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    public CommonResult<String> login(@Valid @RequestBody AdminBO adminBO) {

        Admin admin = adminService.adminLogin(adminBO);


        String token = jwtUtils.createJWTWithPrefix(new Gson().toJson(admin), TOKEN_ADMIN_PREFIX);

        return CommonResult.ok(token);
    }
}
