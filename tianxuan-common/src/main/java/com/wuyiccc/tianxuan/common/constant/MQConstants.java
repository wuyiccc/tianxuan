package com.wuyiccc.tianxuan.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2023/12/16 20:38
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MQConstants {

    /**
     * producer
     */
    public static final String PRODUCER_SMS_CODE = "producer_sms_code";

    /**
     * consumer
     */
    public static final String CONSUMER_SMS_CODE = "consumer_sms_code";


    /**
     * topic
     */
    public static final String TOPIC_SMS_CODE = "topic_sms_code";
}
