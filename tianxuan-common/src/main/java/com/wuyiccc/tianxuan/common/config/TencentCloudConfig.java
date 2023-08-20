package com.wuyiccc.tianxuan.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2023/6/24 16:27
 */
@Component
@Data
@ConfigurationProperties(prefix = "tianxuan.tencent.cloud")
public class TencentCloudConfig {

    private String secretId;
    private String secretKey;
}
