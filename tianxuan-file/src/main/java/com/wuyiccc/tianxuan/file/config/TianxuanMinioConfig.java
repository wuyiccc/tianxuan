package com.wuyiccc.tianxuan.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2023/9/3 07:22
 */
@Component
@Data
@ConfigurationProperties(prefix = "tianxuan.minio")
public class TianxuanMinioConfig {

    private String endpoint;

    private String bucket;

    private String accessKey;

    private String secretKey;
}
