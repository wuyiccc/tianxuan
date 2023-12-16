package com.wuyiccc.tianxuan.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2023/12/16 19:41
 */
@Component
@Data
@ConfigurationProperties("tianxuan.rocketmq")
public class TianxuanRocketMQConfig {

    private String nameServer;

    private Integer pollBatchSize;
}
