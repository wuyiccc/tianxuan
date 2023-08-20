package com.wuyiccc.tianxuan.api.config;

import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.api.interceptor.SMSInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wuyiccc
 * @date 2023/6/26 21:08
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Bean
    public SMSInterceptor smsInterceptor() {
        return new SMSInterceptor();
    }

    @Bean
    public JWTCurrentUserInterceptor jwtCurrentUserInterceptor() {
        return new JWTCurrentUserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // getSMSCode需要做拦截
        registry.addInterceptor(smsInterceptor()).addPathPatterns("/passport/getSMSCode");

        registry.addInterceptor(jwtCurrentUserInterceptor());
    }
}
