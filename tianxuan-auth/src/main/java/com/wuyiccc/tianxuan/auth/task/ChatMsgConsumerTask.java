package com.wuyiccc.tianxuan.auth.task;

import com.wuyiccc.tianxuan.api.config.TianxuanRocketMQConfig;
import com.wuyiccc.tianxuan.auth.service.ChatMessageService;
import com.wuyiccc.tianxuan.common.constant.MQConstants;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * @author wuyiccc
 * @date 2024/6/23 16:26
 */
@Slf4j
public class ChatMsgConsumerTask implements Runnable {

    private TianxuanRocketMQConfig tianxuanRocketMQConfig;


    private ChatMessageService chatMessageService;


    public ChatMsgConsumerTask(TianxuanRocketMQConfig tianxuanRocketMQConfig, ChatMessageService chatMessageService) {
        this.tianxuanRocketMQConfig = tianxuanRocketMQConfig;
        this.chatMessageService = chatMessageService;
    }

    @Override
    public void run() {
        try {

            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MQConstants.CONSUMER_CHAT);
            consumer.setNamesrvAddr(tianxuanRocketMQConfig.getNameServer());
            consumer.subscribe(MQConstants.TOPIC_CHAT, "*");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new ChatMgsConsumerListener(chatMessageService));
            consumer.start();
            log.info("mq消费 {} 开始运行", MQConstants.TOPIC_CHAT);
        } catch (Exception e) {
            log.error("SendSmsCodeConsumerTask mq启动异常", e);
            throw new CustomException("mq启动异常");
        }
    }
}
