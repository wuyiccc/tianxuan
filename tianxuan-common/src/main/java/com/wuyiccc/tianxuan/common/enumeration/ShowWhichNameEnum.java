package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2023/6/27 23:17
 */
public enum ShowWhichNameEnum {

    REALNAME(1, "真实姓名"),
    NICKNAME(2, "昵称")
    ;
    public final Integer code;

    public final String desc;

    ShowWhichNameEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
