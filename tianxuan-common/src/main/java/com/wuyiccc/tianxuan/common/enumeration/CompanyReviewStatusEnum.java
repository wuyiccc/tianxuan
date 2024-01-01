package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2024/1/1 19:56
 */
public enum CompanyReviewStatusEnum {


    /**
     * 审核状态
     0：未发起审核认证(未进入审核流程)
     1：审核认证通过
     2：审核认证失败
     3：审核中（等待审核）
     */
    NOTHING(0, "未发起审核认证"),
    SUCCESSFUL(1, "审核认证通过"),
    FAILED(2, "审核认证失败"),
    REVIEW_ING(3, "审核中");

    public final Integer type;
    public final String value;

    CompanyReviewStatusEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
