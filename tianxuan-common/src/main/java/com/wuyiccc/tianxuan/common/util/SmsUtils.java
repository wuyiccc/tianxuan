package com.wuyiccc.tianxuan.common.util;

import com.wuyiccc.tianxuan.common.config.TencentCloudConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2023/6/24 16:25
 */
@Component
@Slf4j
public class SmsUtils {

    @Autowired
    private TencentCloudConfig tencentCloudConfig;
}

