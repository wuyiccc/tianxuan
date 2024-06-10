package com.wuyiccc.tianxuan.company.service;

import com.wuyiccc.tianxuan.common.enumeration.PayMethodEnum;

/**
 * @author wuyiccc
 * @date 2024/6/10 16:31
 */
public interface OrderService {
    String createOrder(String userId, String companyId, String itemName, PayMethodEnum payMethodEnum, Integer totalAmount);


}
