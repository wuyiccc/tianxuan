package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2024/6/23 13:26
 */
public enum InterviewStatusEnum {

    WAITING(1, "等待候选人接受面试"),
    ACCEPT(2, "候选人已接受面试"),
    REFUSE(3, "候选人已拒绝面试"),
    CANCEL(4, "HR已取消面试"),
    SUCCESS(5, "面试已通过")
    ;
    public final Integer type;

    public final String value;

    InterviewStatusEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
