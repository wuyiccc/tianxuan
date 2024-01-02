package com.wuyiccc.tianxuan.api.feign;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuyiccc
 * @date 2024/1/2 21:43
 */
@FeignClient("tianxuan-user")
public interface UserInfoInnerServiceFeign {

    @PostMapping("/userInfoInner/getCountsByCompanyId")
    CommonResult<Long> getCountsByCompanyId(@RequestParam("companyId") String companyId);
}
