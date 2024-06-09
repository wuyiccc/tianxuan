package com.wuyiccc.tianxuan.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2024/6/8 19:33
 */
@Component
@Data
@ConfigurationProperties("tianxuan.zookeeper")
public class TianxuanZookeeperConfig {


    private String host;

    private Integer timeout;
}
