package com.wuyiccc.tianxuan.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2023/12/16 20:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsCodeDTO {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 短信验证码
     */
    private String smsCode;

    /**
     * 短信发送时间戳
     */
    private Long startTimestamp;
}


