package com.wuyiccc.tianxuan.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.bo.CreateAdminBO;
import com.wuyiccc.tianxuan.pojo.bo.ResetPwdBO;
import com.wuyiccc.tianxuan.pojo.bo.UpdateAdminBO;
import com.wuyiccc.tianxuan.pojo.vo.AdminInfoVO;
import com.wuyiccc.tianxuan.user.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/8/30 20:58
 * 管理端接口
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

    @PostMapping("/list")
    public CommonResult<PagedGridResult> list(String accountName
            , Integer page
            , Integer limit) {


        if (Objects.isNull(page)) {
            page = 1;
        }
        if (Objects.isNull(limit)) {
            limit = 10;
        }

        PagedGridResult res = adminService.getAdminList(accountName, page, limit);

        return CommonResult.ok(res);
    }

    @PostMapping("/delete")
    public CommonResult<String> delete(String username) {


        adminService.delete(username);
        return CommonResult.ok();
    }

    @PostMapping("/resetPassword")
    public CommonResult<String> resetPassword(@RequestBody ResetPwdBO resetPwdBO) {

        adminService.resetPassword(resetPwdBO);

        return CommonResult.ok();
    }

    @GetMapping("myInfo")
    public CommonResult<AdminInfoVO> myInfo() {

        Admin admin = JWTCurrentUserInterceptor.adminUser.get();

        Admin newAdmin = adminService.getById(admin.getId());

        AdminInfoVO adminInfoVO = BeanUtil.copyProperties(newAdmin, AdminInfoVO.class);
        return CommonResult.ok(adminInfoVO);
    }

    @PostMapping("updateMyInfo")
    public CommonResult<String> updateMyInfo(@RequestBody UpdateAdminBO updateAdminBO) {

        Admin admin = JWTCurrentUserInterceptor.adminUser.get();


        updateAdminBO.setId(admin.getId());
        adminService.updateAdmin(updateAdminBO);

        return CommonResult.ok();
    }

}
