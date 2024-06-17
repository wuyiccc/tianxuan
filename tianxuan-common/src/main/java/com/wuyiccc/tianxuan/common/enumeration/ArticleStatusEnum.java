package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2024/6/17 22:43
 */
public enum ArticleStatusEnum {

    CLOSE(0, "关闭，待发布"),
    OPEN(1, "正常，可查阅"),
    DELETE(2, "删除，无法查看")
    ;

    public final Integer type;

    public final String value;


    ArticleStatusEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
