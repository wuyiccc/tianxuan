package com.wuyiccc.tianxuan.user.controller.inner;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
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

    @Resource
    private JWTUtils jwtUtils;


    @PostMapping("getCountsByCompanyId")
    public CommonResult<Long>  getCountsByCompanyId(@RequestParam("companyId") String companyId) {

        Long count = userService.getCountsByCompanyId(companyId);
        return CommonResult.ok(count);
    }

    @PostMapping("bindingHRToCompany")
    public CommonResult<User> bindingHRToCompany(
            @RequestParam("hrUserId") String hrUserId,
            @RequestParam("realname") String realname,
            @RequestParam("companyId") String companyId
    ) {

        userService.updateUserCompanyId(hrUserId, realname, companyId);

        User user = userService.getById(hrUserId);

        return CommonResult.ok(user);
    }

    @PostMapping("get")
    public CommonResult<UserVO> get(@RequestParam("userId") String userId) {

        User user = userService.getById(userId);
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);

        String uToken = jwtUtils.createJWTWithPrefix(JSONUtil.toJsonStr(user), BaseInfoProperties.TOKEN_USER_PREFIX);
        userVO.setUserToken(uToken);
        return CommonResult.ok(userVO);
    }
}
