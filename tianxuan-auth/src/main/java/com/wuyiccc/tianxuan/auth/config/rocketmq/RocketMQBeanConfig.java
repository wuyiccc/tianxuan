package com.wuyiccc.tianxuan.auth.config.rocketmq;

import com.wuyiccc.tianxuan.api.config.TianxuanRocketMQConfig;
import com.wuyiccc.tianxuan.common.constant.MQConstants;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2023/12/16 21:03
 */
@Configuration
public class RocketMQBeanConfig implements ApplicationContextAware {

    @Resource
    private TianxuanRocketMQConfig tianxuanRocketMQConfig;


    private ApplicationContext applicationContext;

    private static final String PRODUCER_BEAN_NAME = "defaultMQProducer";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public DefaultMQProducer defaultMQProducer() {

        DefaultMQProducer producer = new DefaultMQProducer(MQConstants.PRODUCER_SMS_CODE);
        producer.setNamesrvAddr(tianxuanRocketMQConfig.getNameServer());
        return producer;
    }

    @Bean
    public RocketMQTemplateConfig rocketMQTemplateConfig() {

        RocketMQTemplateConfig rocketMQTemplateConfig = new RocketMQTemplateConfig();

        if (applicationContext.containsBean(PRODUCER_BEAN_NAME)) {
            rocketMQTemplateConfig.setProducer((DefaultMQProducer) applicationContext.getBean(PRODUCER_BEAN_NAME));
        }
        return rocketMQTemplateConfig;
    }



}
