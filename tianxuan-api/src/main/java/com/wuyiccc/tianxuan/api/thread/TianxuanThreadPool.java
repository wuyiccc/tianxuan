package com.wuyiccc.tianxuan.api.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyiccc
 * @date 2024/2/23 00:14
 */
@Component
public class TianxuanThreadPool {


    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                3
                , 10
                , 30
                , TimeUnit.SECONDS
                , new LinkedBlockingQueue<>(5000)
                , Executors.defaultThreadFactory()
                , new ThreadPoolExecutor.AbortPolicy());
    }
}
