package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2024/1/1 19:53
 */
public enum YesOrNoEnum {

    YES(1, "是"),
    NO(0, "否")
    ;

    public final Integer type;

    public final String desc;


    YesOrNoEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
