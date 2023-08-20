package com.wuyiccc.tianxuan.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/7/23 06:43
 */
@Component
@Data
@ConfigurationProperties(prefix = "tianxuan.black.ip")
public class TianxuanBlackIpConfig {

    private Integer continueCounts;

    private Integer timeInterval;

    private Integer limitTimes;

    private List<String> limitUrls;
}
