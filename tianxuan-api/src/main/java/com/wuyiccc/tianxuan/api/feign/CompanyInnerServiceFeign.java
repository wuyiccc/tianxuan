package com.wuyiccc.tianxuan.api.feign;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/5/29 23:10
 */
@FeignClient("tianxuan-company")
public interface CompanyInnerServiceFeign {

    @PostMapping("/companyInner/getList")
    CommonResult<List<CompanyInfoVO>> getList(@RequestBody List<String> companyIdList);
}
