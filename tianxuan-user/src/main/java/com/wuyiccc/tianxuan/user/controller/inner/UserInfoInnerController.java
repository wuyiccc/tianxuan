package com.wuyiccc.tianxuan.user.controller.inner;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/1/2 21:30
 */
@RestController
@RequestMapping("/userInfoInner")
@Slf4j
public class UserInfoInnerController {

    @Resource
    private UserService userService;


    @PostMapping("getCountsByCompanyId")
    public CommonResult<Long>  getCountsByCompanyId(@RequestParam("companyId") String companyId) {

        Long count = userService.getCountsByCompanyId(companyId);
        return CommonResult.ok(count);
    }
}
