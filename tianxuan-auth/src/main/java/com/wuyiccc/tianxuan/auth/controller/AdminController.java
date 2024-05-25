package com.wuyiccc.tianxuan.auth.controller;

import cn.hutool.json.JSONUtil;
import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.auth.service.AdminService;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.bo.AdminBO;
import com.wuyiccc.tianxuan.pojo.vo.AdminVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyiccc
 * @date 2023/8/22 22:32
 * 管理端接口
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController extends BaseInfoProperties {


    @Autowired
    private AdminService adminService;

    @Autowired
    private JWTUtils jwtUtils;

    @Resource
    private RedissonClient redissonClient;

    @PostMapping("/login")
    public CommonResult<String> login(@Valid @RequestBody AdminBO adminBO) {

        Admin admin = adminService.adminLogin(adminBO);
        String token = jwtUtils.createJWTWithPrefix(JSONUtil.toJsonStr(admin), TOKEN_ADMIN_PREFIX);
        return CommonResult.ok(token);
    }


    @GetMapping("/info")
    public CommonResult<AdminVO> info() {

        Admin admin = JWTCurrentUserInterceptor.adminUser.get();
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(admin, adminVO);
        return CommonResult.ok(adminVO);
    }

    @PostMapping("/logout")
    public CommonResult<String> logout() {
//        redisUtils.del(REDIS_USER_TOKEN + ":" + userId);
        return CommonResult.ok();
    }


    @GetMapping("/hello")
    public CommonResult<String> hello() throws InterruptedException {

        RLock lock1 = redissonClient.getLock("lock1");
        RLock lock2 = redissonClient.getLock("lock2");
        RLock lock3 = redissonClient.getLock("lock3");
        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);

        lock.lock();

        TimeUnit.SECONDS.sleep(10);

        lock.unlock();

        return CommonResult.ok();
    }

}
