package com.wuyiccc.tianxuan.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
@TableName("`order`")
public class Order {


    private String id;

    /**
     * 用户主键id
     */
    private String userId;

    /**
     * 购买用户所在的企业主键id
     */
    private String companyId;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 订单总价
     */
    private Integer totalAmount;

    /**
     * 实际支付总价格
     */
    private Integer realPayAmount;

    /**
     * 邮费;默认可以为零，代表包邮
     */
    private Integer postAmount;

    /**
     * 支付方式
     */
    private Integer payMethod;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 扩展字段
     */
    private String extend;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
