package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2023/6/27 23:19
 */
public enum SexEnum {

    WOMAN(0, "女"),
    MAN(1, "男"),
    SECRET(2, "保密");
    ;
    public final Integer code;

    public final String desc;

    SexEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
