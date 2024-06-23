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

    private static DefaultMQProducer producer = null;


    public static void start(String nameServer) throws MQClientException {
        producer = new DefaultMQProducer(MQConstants.PRODUCER_CHAT);
        producer.setNamesrvAddr(nameServer);
        producer.start();
    }

    public static void sendMsg(String msg) {
        try {
            producer.send(new Message(MQConstants.TOPIC_CHAT, msg.getBytes()));
        } catch (Throwable e) {
            log.error("消息发送失败", e);
        }
    }

    public static void stop() {

        producer.shutdown();
    }

}
