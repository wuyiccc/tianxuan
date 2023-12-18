package com.wuyiccc.tianxuan.api.feign;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuyiccc
 * @date 2023/12/18 21:10
 */
@FeignClient("tianxuan-work")
public interface WorkMicroServiceFeign {


    /**
     * 初始化用户简历
     */
    @PostMapping("/resume/init")
    public CommonResult<String> init(@RequestParam("userId") String userId);

}
