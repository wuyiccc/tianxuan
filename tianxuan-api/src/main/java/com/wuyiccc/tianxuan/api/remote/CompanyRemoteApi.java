package com.wuyiccc.tianxuan.api.remote;

import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/5/29 23:10
 */
@FeignClient("tianxuan-company")
public interface CompanyRemoteApi {

    @PostMapping("/companyInner/getList")
    R<List<CompanyInfoVO>> getList(@RequestBody List<String> companyIdList);
}
