package com.wuyiccc.chat.config;

import com.wuyiccc.tianxuan.common.constant.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author wuyiccc
 * @date 2024/6/23 16:06
 */
@Slf4j
public class MyMqClient {

    private static DefaultMQProducer producer = null;

    private static final String CHAT_CONFIG_FILE_NAME = "chat.properties";

    private static final String NAME_SERVER_CONFIG_KEY = "tianxuan.rocketmq.nameserver";


    public static void start() throws MQClientException {

        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CHAT_CONFIG_FILE_NAME);
        try {
            properties.load(in);
        } catch (Exception e) {
            log.error("加载chat.properties文件失败", e);
        }

        String nameserver = properties.getProperty(NAME_SERVER_CONFIG_KEY);
        log.info("this is nameserver addr: {}", nameserver);

        producer = new DefaultMQProducer(MQConstants.PRODUCER_CHAT);
        producer.setNamesrvAddr(nameserver);
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
