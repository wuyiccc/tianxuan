package com.wuyiccc.tianxuan.api;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2023/12/11 20:18
 */
@Slf4j
@Component
@Aspect
public class ServiceLogAspect {


    @Around("execution(* com.wuyiccc.tianxuan.*.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        long begin = System.currentTimeMillis();

        Object process = joinPoint.proceed();

        long end = System.currentTimeMillis();

        String point = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();

        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("执行方法 {} 时间太长了, 耗费了{}毫秒", point, takeTime);
        } else if (takeTime > 2000) {
            log.warn("执行方法 {} 时间稍微有点长, 耗费了{}毫秒", point, takeTime);
        } else {
            log.info("执行方法 {} 时间, 耗费了{}毫秒", point, takeTime);
        }
        return process;
    }
}
