package com.wuyiccc.tianxuan.company.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.enumeration.PayMethodEnum;
import com.wuyiccc.tianxuan.common.enumeration.PaymentStatusEnum;
import com.wuyiccc.tianxuan.common.enumeration.YesOrNoEnum;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.company.mapper.CompanyMapper;
import com.wuyiccc.tianxuan.company.mapper.OrderMapper;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.company.service.OrderService;
import com.wuyiccc.tianxuan.pojo.Company;
import com.wuyiccc.tianxuan.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private CompanyService companyService;

    @Resource
    private RedisUtils redisUtils;

    @Transactional(rollbackFor = Exception.class)
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
        Company company = companyMapper.selectById(companyId);
        boolean isVip = companyService.isVip(companyId);
        LocalDate vipExpireDate = LocalDate.now();
        if (isVip) {
            vipExpireDate = company.getVipExpireDate();
        }
        LocalDate newExpireDate = LocalDateUtils.plus(vipExpireDate, 1, ChronoUnit.MONTHS);
        // 更新vip信息
        Company newCompany = new Company();
        newCompany.setId(companyId);
        newCompany.setIsVip(YesOrNoEnum.YES.type);
        newCompany.setVipExpireDate(newExpireDate);
        int res = companyMapper.updateById(newCompany);
        if (res != 1) {
            throw new CustomException("更新企业vip信息失败");
        }
        redisUtils.del(BaseInfoProperties.REDIS_COMPANY_IS_VIP + StrPool.COLON + companyId);
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
