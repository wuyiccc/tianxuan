package com.wuyiccc.tianxuan.company.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.api.feign.UserInfoInnerServiceFeign;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.pojo.Company;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.bo.CreateCompanyBO;
import com.wuyiccc.tianxuan.pojo.bo.ReviewCompanyBO;
import com.wuyiccc.tianxuan.pojo.vo.CompanySimpleVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/6/19 22:59
 * app接口
 */
@RestController
@RequestMapping("/company")
public class CompanyController {


    @Resource
    private CompanyService companyService;

    @Resource
    private UserInfoInnerServiceFeign userInfoInnerServiceFeign;

    @PostMapping("/getByFullName")
    public CommonResult<CompanySimpleVO> getByFullName(String fullName) {

        if (CharSequenceUtil.isBlank(fullName)) {
            return CommonResult.error();
        }

        Company company = companyService.getByFullName(fullName);

        if (Objects.isNull(company)) {
            return CommonResult.ok();
        }

        return CommonResult.ok(BeanUtil.copyProperties(company, CompanySimpleVO.class));
    }

    @PostMapping("createNewCompany")
    public CommonResult<String> createNewCompany(@RequestBody CreateCompanyBO createCompanyBO) {

        String companyId = createCompanyBO.getCompanyId();
        if (CharSequenceUtil.isBlank(companyId)) {
            // 创建公司
            companyId = companyService.createNewCompany(createCompanyBO);
        } else {
            // 修改公司
            companyId = companyService.resetNewCompany(createCompanyBO);
        }

        return CommonResult.ok(companyId);
    }

    @PostMapping("getInfo")
    public CommonResult<CompanySimpleVO> getInfo(String companyId, boolean withHRCounts) {

        CompanySimpleVO companySimpleVO = companyService.getCompany(companyId);

        if (withHRCounts) {

            CommonResult<Long> result = userInfoInnerServiceFeign.getCountsByCompanyId(companyId);
            companySimpleVO.setHrCounts(result.getData());
        }

        return CommonResult.ok(companySimpleVO);
    }

    @PostMapping("goReviewCompany")
    public CommonResult<String> goReviewCompany(@RequestBody @Valid ReviewCompanyBO reviewCompanyBO) {


        // 微服务调用, 绑定HR企业id
        CommonResult<User> userCommonResult = userInfoInnerServiceFeign.bindingHRToCompany(reviewCompanyBO.getHrUserId(), reviewCompanyBO.getRealname(), reviewCompanyBO.getCompanyId());



        return CommonResult.error();
    }
}
