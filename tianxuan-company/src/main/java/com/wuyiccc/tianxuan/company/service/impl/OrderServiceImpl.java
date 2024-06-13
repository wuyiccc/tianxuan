package com.wuyiccc.tianxuan.company.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.common.enumeration.PayMethodEnum;
import com.wuyiccc.tianxuan.common.enumeration.PaymentStatusEnum;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import com.wuyiccc.tianxuan.company.mapper.OrderMapper;
import com.wuyiccc.tianxuan.company.service.OrderService;
import com.wuyiccc.tianxuan.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/10 16:31
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public String createOrder(String userId, String companyId, String itemName, PayMethodEnum payMethodEnum, Integer totalAmount) {

        String prefix = LocalDateUtils.format(LocalDateTime.now(), LocalDateUtils.DATETIME_PATTERN_3);

        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String sid = snowflake.nextIdStr();

        String orderNum = prefix + sid;


        Order order = new Order();
        order.setId(orderNum);
        order.setUserId(userId);
        order.setCompanyId(companyId);
        order.setItemName(itemName);
        order.setTotalAmount(totalAmount);
        order.setRealPayAmount(totalAmount);
        order.setPayMethod(payMethodEnum.type);
        order.setPostAmount(0);
        order.setStatus(PaymentStatusEnum.PAID.type);
        order.setCreatedTime(LocalDateTime.now());
        order.setUpdatedTime(LocalDateTime.now());


        orderMapper.insert(order);

        // 设置企业为vip用户

        return order.getId();
    }

    @Override
    public PagedGridResult pageList(String companyId, Integer page, Integer limit) {

        PageHelper.startPage(page, limit);
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getCompanyId, companyId);
        List<Order> orderList = orderMapper.selectList(wrapper);
        return PagedGridResult.build(orderList, page);
    }


}
