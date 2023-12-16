package com.wuyiccc.tianxuan.auth.task;

import com.wuyiccc.tianxuan.api.config.TianxuanRocketMQConfig;
import com.wuyiccc.tianxuan.common.constant.MQConstants;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.util.DingDingMsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * @author wuyiccc
 * @date 2023/12/16 21:30
 */
@Slf4j
public class SendSmsCodeConsumerTask implements Runnable{

    private TianxuanRocketMQConfig tianxuanRocketMQConfig;

    private DingDingMsgUtils dingDingMsgUtils;


    public SendSmsCodeConsumerTask(TianxuanRocketMQConfig tianxuanRocketMQConfig, DingDingMsgUtils dingDingMsgUtils) {
        this.tianxuanRocketMQConfig = tianxuanRocketMQConfig;
        this.dingDingMsgUtils = dingDingMsgUtils;
    }

    @Override
    public void run() {
        try {

            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MQConstants.CONSUMER_SMS_CODE);
            consumer.setNamesrvAddr(tianxuanRocketMQConfig.getNameServer());
            consumer.subscribe(MQConstants.TOPIC_SMS_CODE, "*");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new SendSMSCodeConsumerListener(dingDingMsgUtils));
            consumer.start();
            log.info("mq消费 {} 开始运行", MQConstants.TOPIC_SMS_CODE);
        } catch (Exception e) {
            log.error("SendSmsCodeConsumerTask mq启动异常", e);
            throw new CustomException("mq启动异常");
        }
    }
}
