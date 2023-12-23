package com.wuyiccc.tianxuan.api.interceptor;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2023/7/1 22:43
 */
@Slf4j
public class JWTCurrentUserInterceptor extends BaseInfoProperties implements HandlerInterceptor {

    public static ThreadLocal<User> currentUser = new ThreadLocal<>();
    public static ThreadLocal<Admin> adminUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {


        String appUserJson = request.getHeader(APP_USER_JSON);
        String saasUserJson = request.getHeader(SAAS_USER_JSON);
        String adminUserJson = request.getHeader(ADMIN_USER_JSON);

        if (StringUtils.isNotBlank(appUserJson)) {
            User appUser = JSONUtil.toBean(appUserJson, User.class);
            currentUser.set(appUser);
        }

        if (StringUtils.isNotBlank(saasUserJson)) {
            User saasUser = JSONUtil.toBean(saasUserJson, User.class);
            currentUser.set(saasUser);
        }

        if (StringUtils.isNotBlank(adminUserJson)) {
            Admin admin = JSONUtil.toBean(adminUserJson, Admin.class);
            adminUser.set(admin);
        }

        /**
         * false: 请求被拦截
         * true: 放行，请求验证通过
         */
        return true;
    }


    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setUpdatedTime(LocalDateTime.now());
        user.setBirthday(LocalDate.now());
        String s = objectMapper.writeValueAsString(user);
        System.out.println(s);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        currentUser.remove();
        adminUser.remove();
    }
}
