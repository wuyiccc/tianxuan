package com.wuyiccc.tianxuan.api.interceptor;

import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyiccc
 * @date 2023/6/26 21:01
 */
@Slf4j
public class SMSInterceptor extends BaseInfoProperties implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestIp = IPUtils.getRequestIp(request);

        boolean ipExist = redisUtils.keyIsExist(MOBILE_SMSCODE + ":" + requestIp);
        if (ipExist) {
            throw new CustomException(ResponseStatusEnum.SMS_NEED_WAIT_ERROR);
        }

        // true: 放行
        return true;
    }

}
