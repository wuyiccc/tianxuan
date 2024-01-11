package com.wuyiccc.tianxuan.company.controller;

import com.wuyiccc.tianxuan.api.feign.UserInfoInnerServiceFeign;
import com.wuyiccc.tianxuan.common.enumeration.CompanyReviewStatusEnum;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.pojo.bo.QueryCompanyBO;
import com.wuyiccc.tianxuan.pojo.bo.ReviewCompanyBO;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/1/8 21:28
 */
@RestController
@RequestMapping("/adminCompany")
public class AdminCompanyController {


    @Resource
    private CompanyService companyService;


    @Resource
    private UserInfoInnerServiceFeign userInfoInnerServiceFeign;

    @PostMapping("getCompanyList")
    public CommonResult<PagedGridResult> getCompanyList(@RequestBody @Valid QueryCompanyBO queryCompanyBO
            , Integer page
            , Integer limit) {

        if (Objects.isNull(page)) {
            page = 1;
        }
        if (Objects.isNull(limit)) {
            limit = 10;
        }

        PagedGridResult pagedGridResult = companyService.getCompanyList(queryCompanyBO, page, limit);
        return CommonResult.ok(pagedGridResult);
    }

    @PostMapping("getCompanyInfo")
    public CommonResult<CompanyInfoVO> getCompanyInfo(String companyId) {

        CompanyInfoVO companyInfoVO = companyService.getCompanyInfo(companyId);
        return CommonResult.ok(companyInfoVO);
    }

    @PostMapping("doReview")
    public CommonResult<String> doReview(@RequestBody @Valid ReviewCompanyBO reviewCompanyBO) {


        // 1. 审核企业
        companyService.updateReviewInfo(reviewCompanyBO);

        if (CompanyReviewStatusEnum.SUCCESSFUL.type.equals(reviewCompanyBO.getReviewStatus())) {
            userInfoInnerServiceFeign.changeUserToHR(reviewCompanyBO.getHrUserId());
        }
        return CommonResult.ok();
    }

}