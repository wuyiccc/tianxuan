package com.wuyiccc.tianxuan.auth.controller;

import com.google.gson.Gson;
import com.wuyiccc.tianxuan.auth.service.UserService;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.DingDingMsgUtils;
import com.wuyiccc.tianxuan.common.util.IPUtils;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import com.wuyiccc.tianxuan.common.util.SmsUtils;
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
 */
@RestController
@RequestMapping("/passport")
@Slf4j
public class PassportController extends BaseInfoProperties {

    @Autowired
    private SmsUtils smsUtils;


    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private DingDingMsgUtils dingDingMsgUtils;

    @PostMapping("/getSMSCode")
    public CommonResult<String> getSMSCode(String mobile, HttpServletRequest request) {

        if (StringUtils.isBlank(mobile)) {
            return CommonResult.errorMsg("手机号不可为空");
        }

        // 限制用户只能在60s以内获得一次验证码
        String requestIp = IPUtils.getRequestIp(request);
        redisUtils.setnx60s(MOBILE_SMSCODE + ":" + requestIp, mobile);

        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        log.info("手机号: {}, 当前验证码为: {}", mobile, code);
        // TODO(wuyiccc): 发送短信验证码
//            smsUtils.sendSMS(mobile, code);
        dingDingMsgUtils.sendSMSCode(code);
        // 设置验证码过期时间为30min
        redisUtils.set(MOBILE_SMSCODE + ":" + mobile, code, 30 * 60);
        return CommonResult.ok("发送短信成功");
    }

    @PostMapping("/login")
    public CommonResult<UserVO> login(@Valid @RequestBody RegisterLoginBO registerLoginBO
    , HttpServletRequest request) {
        String mobile = registerLoginBO.getMobile();
        String smsCode = registerLoginBO.getSmsCode();

        String sendSmsCode = redisUtils.get(MOBILE_SMSCODE + ":" + mobile);

        if (!smsCode.equalsIgnoreCase(sendSmsCode)) {
            return CommonResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }
        redisUtils.del(MOBILE_SMSCODE + ":" + mobile);

        User user = userService.queryUserByMobile(mobile);
        if (Objects.isNull(user)) {
            user = userService.createUser(mobile);
        }

        String uToken = jwtUtils.createJWTWithPrefix(new Gson().toJson(user), TOKEN_USER_PREFIX);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setUserToken(uToken);

        return CommonResult.ok(userVO);
    }

    @PostMapping("/logout")
    public CommonResult<String> logout(@RequestParam String userId) {
//        redisUtils.del(REDIS_USER_TOKEN + ":" + userId);
        return CommonResult.ok();
    }
}
