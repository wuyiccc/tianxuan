package com.wuyiccc.tianxuan.auth.controller;

import com.wuyiccc.tianxuan.auth.service.AdminService;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.bo.AdminLoginBO;
import com.wuyiccc.tianxuan.pojo.vo.AdminUserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/8/20 11:06
 */
@RestController
@RequestMapping("/adminPassport")
@Slf4j
public class AdminPassportController extends BaseInfoProperties {


    @Autowired
    private AdminService adminService;




    @PostMapping("/login")
    public CommonResult<AdminUserLoginVO> login(@Valid @RequestBody AdminLoginBO registerLoginBO
            , HttpServletRequest request) {

        return CommonResult.ok(adminService.login(registerLoginBO));
    }

    @PostMapping("/logout")
    public CommonResult<String> logout(@RequestParam String userId) {
//        redisUtils.del(REDIS_USER_TOKEN + ":" + userId);
        return CommonResult.ok();
    }

    @PostMapping("/createAdmin")
    public CommonResult<String> createAdmin(@RequestBody Admin admin) {

        adminService.createAdmin(admin);
        return CommonResult.ok("创建成功");
    }

    @GetMapping("/getAdminById")
    public CommonResult<Admin> getAdminById(@RequestParam String id) {

        Admin byId = adminService.getById(id);
        return CommonResult.ok(byId);
    }


    @PostMapping("/updateAdmin")
    public CommonResult<String> updateAdmin(@RequestBody Admin admin) {

        adminService.updateAdmin(admin);
        return CommonResult.ok();
    }

    @PostMapping("/list")
    public CommonResult<List<Admin>> list(@RequestBody Admin admin) {
        List<Admin> res = adminService.findAll(admin);
        return CommonResult.ok(res);
    }

    @PostMapping("/deleteAdmin")
    public CommonResult<String> deleteAdmin(@RequestParam String id) {
        adminService.removeById(id);
        return CommonResult.ok();
    }
}
