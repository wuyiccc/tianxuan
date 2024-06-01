package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2024/6/1 16:15
 */
public enum DealStatusEnum {


    WAITING(0, "待处理"),
    DONE(1, "已处理"),
    IGNORE(2, "已忽略，无需处理"),
    ;
    public final Integer type;
    public final String value;

    DealStatusEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
