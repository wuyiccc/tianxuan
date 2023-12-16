package com.wuyiccc.tianxuan.auth.config.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/12/16 21:07
 */
@Slf4j
public class RocketMQTemplateConfig implements InitializingBean, DisposableBean {

    private DefaultMQProducer producer;

    public void setProducer(DefaultMQProducer producer) {
        this.producer = producer;
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        log.info("RocketMQTemplateConfig afterPropertiesSet 执行");
        if (Objects.nonNull(producer)) {
            producer.start();
        }
    }

    @Override
    public void destroy() {

        log.info("RocketMQTemplateConfig destroy 执行");

        if (Objects.nonNull(producer)) {
            producer.shutdown();
        }
    }
}
