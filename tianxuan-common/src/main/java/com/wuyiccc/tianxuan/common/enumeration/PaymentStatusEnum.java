package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2024/6/10 21:01
 */
public enum PaymentStatusEnum {

    WAIT_PAY(10, "未支付"),
    PAID(20, "已支付"),
    PAY_FAILED(30, "支付失败"),
    SUCCESS(40, "已退款");

    public final Integer type;
    public final String value;

    PaymentStatusEnum(Integer type, String value){
        this.type = type;
        this.value = value;
    }
}
