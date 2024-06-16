package com.wuyiccc.tianxuan.auth.controller;

import cn.hutool.json.JSONUtil;
import com.wuyiccc.tianxuan.auth.service.UserService;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.DingDingMsgUtils;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.bo.RegisterLoginBO;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/6/24 22:39
 * 移动端接口
 */
@RestController
@RequestMapping("/passport")
@Slf4j
public class PassportController extends BaseInfoProperties {


    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private DingDingMsgUtils dingDingMsgUtils;

    @PostMapping("/getSMSCode")
    public R<String> getSMSCode(String mobile, HttpServletRequest request) {

        if (StringUtils.isBlank(mobile)) {
            return R.errorMsg("手机号不可为空");
        }

        userService.getSMSCode(mobile, request);


        return R.ok("发送短信成功");
    }

    @PostMapping("/login")
    public R<UserVO> login(@Valid @RequestBody RegisterLoginBO registerLoginBO
            , HttpServletRequest request) {
        String mobile = registerLoginBO.getMobile();
        String smsCode = registerLoginBO.getSmsCode();

        String sendSmsCode = redisUtils.get(MOBILE_SMSCODE + ":" + mobile);

        if (!smsCode.equalsIgnoreCase(sendSmsCode)) {
            return R.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }
        redisUtils.del(MOBILE_SMSCODE + ":" + mobile);

        User user = userService.queryUserByMobile(mobile);
        if (Objects.isNull(user)) {
            user = userService.createUser(mobile);
        }

        String uToken = jwtUtils.createJWTWithPrefix(JSONUtil.toJsonStr(user), TOKEN_USER_PREFIX);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setUserToken(uToken);

        return R.ok(userVO);
    }

    @PostMapping("/logout")
    public R<String> logout(@RequestParam String userId) {
//        redisUtils.del(REDIS_USER_TOKEN + ":" + userId);
        return R.ok();
    }
}
