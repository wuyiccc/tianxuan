package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2024/6/22 17:19
 */
public enum AggUserTypeEnum {

    USER(1, "新注册普通用户"),
    HR(2, "入驻成功的HR用户"),
    COMPANY(3, "审核成功的企业用户"),
    ENTRY(4, "成功入职的用户"),
    SEND(5, "投递发送简历的用户")
    ;

    public final Integer type;
    public final String value;

    AggUserTypeEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
