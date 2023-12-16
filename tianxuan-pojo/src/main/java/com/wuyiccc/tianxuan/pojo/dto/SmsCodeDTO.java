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

    private String mobile;

    private String smsCode;
}


