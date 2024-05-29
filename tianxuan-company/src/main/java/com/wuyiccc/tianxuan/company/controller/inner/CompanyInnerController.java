package com.wuyiccc.tianxuan.company.controller.inner;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/5/29 23:10
 */
@RestController
@RequestMapping("/companyInner")
public class CompanyInnerController {

    @Resource
    private CompanyService companyService;


    @PostMapping("/getList")
    public CommonResult<List<CompanyInfoVO>> getList(@RequestBody List<String> companyIdList) {

        List<CompanyInfoVO> resList = companyService.getList(companyIdList);

        return CommonResult.ok(resList);
    }
}
