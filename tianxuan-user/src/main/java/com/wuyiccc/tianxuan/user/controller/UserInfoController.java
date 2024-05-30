package com.wuyiccc.tianxuan.user.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.bo.ModifyUserBO;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
import com.wuyiccc.tianxuan.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2023/12/22 20:49
 * app system api
 */
@RestController
@RequestMapping("/userInfo")
@Slf4j
public class UserInfoController {

    @Resource
    private UserService userService;

    @Resource
    private JWTUtils jwtUtils;


    @PostMapping("/modify")
    public CommonResult<UserVO> modify(@RequestBody ModifyUserBO modifyUserBO) {

        userService.modifyUserInfo(modifyUserBO);

        User user = userService.getById(modifyUserBO.getUserId());


        // re-generate old token
        String uToken = jwtUtils.createJWTWithPrefix(JSONUtil.toJsonStr(user), BaseInfoProperties.TOKEN_USER_PREFIX);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        userVO.setUserToken(uToken);

        return CommonResult.ok(userVO);
    }

    @PostMapping("/freshUserInfo")
    public CommonResult<UserVO> freshUserInfo(@RequestParam("userId") String userId) {

        User user = userService.getById(userId);
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);

        return CommonResult.ok(userVO);
    }

    @PostMapping("/changeUserToCand")
    public CommonResult<String> changeUserToCand(@RequestParam("hrUserId") String hrUserId) {
        userService.changeUserToCand(hrUserId);
        return CommonResult.ok();
    }

    @PostMapping("/getInfo")
    public CommonResult<User> getInfo(@RequestParam String userId) {

        User user = userService.getById(userId);

        return CommonResult.ok(user);
    }

}
