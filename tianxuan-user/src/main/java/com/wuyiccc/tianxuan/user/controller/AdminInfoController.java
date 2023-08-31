package com.wuyiccc.tianxuan.user.controller;

import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.bo.CreateAdminBO;
import com.wuyiccc.tianxuan.user.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author wuyiccc
 * @date 2023/8/30 20:58
 */
@RestController
@RequestMapping("/adminInfo")
@Slf4j
public class AdminInfoController extends BaseInfoProperties {



    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public CommonResult<String> create(@Valid @RequestBody CreateAdminBO createAdminBO) {
        adminService.createAdmin(createAdminBO);
        return CommonResult.ok();
    }
}
