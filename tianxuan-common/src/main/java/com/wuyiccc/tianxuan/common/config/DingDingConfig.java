package com.wuyiccc.tianxuan.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2023/7/2 08:13
 */
@Component
@Data
@ConfigurationProperties("tianxuan.dingding")
public class DingDingConfig {

    private String robotAccessUrl;

    private String secret;
}
