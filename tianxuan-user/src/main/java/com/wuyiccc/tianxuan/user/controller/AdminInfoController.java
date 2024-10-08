package com.wuyiccc.tianxuan.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.R;
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
    public R<String> create(@Valid @RequestBody CreateAdminBO createAdminBO) {
        adminService.createAdmin(createAdminBO);
        return R.ok();
    }

    @PostMapping("/list")
    public R<PagedGridResult> list(String accountName
            , Integer page
            , Integer limit) {


        if (Objects.isNull(page)) {
            page = 1;
        }
        if (Objects.isNull(limit)) {
            limit = 10;
        }

        PagedGridResult res = adminService.getAdminList(accountName, page, limit);

        return R.ok(res);
    }

    @PostMapping("/delete")
    public R<String> delete(String username) {


        adminService.delete(username);
        return R.ok();
    }

    @PostMapping("/resetPassword")
    public R<String> resetPassword(@RequestBody ResetPwdBO resetPwdBO) {

        adminService.resetPassword(resetPwdBO);

        return R.ok();
    }

    @GetMapping("myInfo")
    public R<AdminInfoVO> myInfo() {

        Admin admin = JWTCurrentUserInterceptor.adminUser.get();

        Admin newAdmin = adminService.getById(admin.getId());

        AdminInfoVO adminInfoVO = BeanUtil.copyProperties(newAdmin, AdminInfoVO.class);
        return R.ok(adminInfoVO);
    }

    @PostMapping("updateMyInfo")
    public R<String> updateMyInfo(@RequestBody UpdateAdminBO updateAdminBO) {

        Admin admin = JWTCurrentUserInterceptor.adminUser.get();


        updateAdminBO.setId(admin.getId());
        adminService.updateAdmin(updateAdminBO);

        return R.ok();
    }

}
