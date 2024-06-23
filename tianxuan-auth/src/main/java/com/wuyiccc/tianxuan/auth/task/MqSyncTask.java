package com.wuyiccc.tianxuan.auth.task;

import com.wuyiccc.tianxuan.api.config.TianxuanRocketMQConfig;
import com.wuyiccc.tianxuan.auth.service.ChatMessageService;
import com.wuyiccc.tianxuan.common.util.DingDingMsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wuyiccc
 * @date 2023/8/2 15:33
 */

@Slf4j
@Component
public class MqSyncTask implements ApplicationRunner {


    @Resource
    private TianxuanRocketMQConfig tianxuanRocketMQConfig;


    @Resource
    private DingDingMsgUtils dingDingMsgUtils;

    @Resource
    private ChatMessageService chatMessageService;


    @Override
    public void run(ApplicationArguments args) {

        ExecutorService executors = Executors.newFixedThreadPool(2);
        executors.submit(new SendSmsCodeConsumerTask(tianxuanRocketMQConfig, dingDingMsgUtils));
        executors.submit(new ChatMsgConsumerTask(tianxuanRocketMQConfig, chatMessageService));
    }
}
