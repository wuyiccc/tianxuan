package com.wuyiccc.tianxuan.common.enumeration;


public enum JobStatusEnum {
    OPEN(1, "招聘中"),
    CLOSE(2, "已关闭"),
    DELETE(3, "违规删除");

    public final Integer code;
    public final String value;

    JobStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
