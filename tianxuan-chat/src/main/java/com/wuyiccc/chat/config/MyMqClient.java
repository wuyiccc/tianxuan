package com.wuyiccc.chat.config;

import com.wuyiccc.tianxuan.common.constant.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author wuyiccc
 * @date 2024/6/23 16:06
 */
@Slf4j
public class MyMqClient {

    public static void main(String[] args) throws MQClientException {

        DefaultMQProducer producer = new DefaultMQProducer(MQConstants.PRODUCER_CHAT);
        producer.setNamesrvAddr("rocketmq.local.wuyiccc.com:12071");
        producer.start();


        try {
            producer.send(new Message(MQConstants.TOPIC_CHAT, "hello localhost:9876".getBytes()));
        } catch (Throwable e) {
            log.error("消息发送失败", e);
        }
        producer.shutdown();
    }

}
