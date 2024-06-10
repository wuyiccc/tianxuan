package com.wuyiccc.tianxuan.company.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.enumeration.PayMethodEnum;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.company.service.OrderService;
import com.wuyiccc.tianxuan.pojo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/10 17:36
 */
@RestController
@RequestMapping("/tradeOrder")
public class TradeOrderController {

    @Resource
    private OrderService orderService;


    @PostMapping("/create")
    public CommonResult<String> create() {

        User hrUser = JWTCurrentUserInterceptor.currentUser.get();
        String userId = hrUser.getId();
        String companyId = hrUser.getHrInWhichCompanyId();

        if (CharSequenceUtil.isBlank(userId) || CharSequenceUtil.isBlank(companyId)) {
            return CommonResult.ok("用户信息有误~");
        }

        String url = orderService.createOrder(userId, companyId, "VIP企业会员-1个月", PayMethodEnum.WEI_XIN, 1);

        return CommonResult.ok(url);
    }

    @PostMapping("/generatorWXPayQRCode")
    public CommonResult<String> generatorWXPayQRCode(@RequestParam String merchantOrderId) {


        String payUrl = "https://%s.merchatOrderId.wuyiccc.com/";

        payUrl = String.format(payUrl, merchantOrderId);

        return CommonResult.ok(payUrl);
    }

}
