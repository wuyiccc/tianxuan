package com.wuyiccc.tianxuan.api.interceptor;

import com.google.gson.Gson;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            User appUser = new Gson().fromJson(appUserJson, User.class);
            currentUser.set(appUser);
        }

        if (StringUtils.isNotBlank(saasUserJson)) {
            User saasUser = new Gson().fromJson(saasUserJson, User.class);
            currentUser.set(saasUser);
        }

        if (StringUtils.isNotBlank(adminUserJson)) {
            Admin admin = new Gson().fromJson(adminUserJson, Admin.class);
            adminUser.set(admin);
        }

        /**
         * false: 请求被拦截
         * true: 放行，请求验证通过
         */
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        currentUser.remove();
        adminUser.remove();
    }
}
