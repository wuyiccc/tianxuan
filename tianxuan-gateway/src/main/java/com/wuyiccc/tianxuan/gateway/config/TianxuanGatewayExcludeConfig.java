package com.wuyiccc.tianxuan.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/7/1 17:07
 */
@Component
@Data
@ConfigurationProperties(prefix = "tianxuan.exclude")
public class TianxuanGatewayExcludeConfig {

    private List<String> urls;
}
