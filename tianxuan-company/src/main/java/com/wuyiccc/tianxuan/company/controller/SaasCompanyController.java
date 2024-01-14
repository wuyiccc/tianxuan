package com.wuyiccc.tianxuan.company.controller;

import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.pojo.CompanyPhoto;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import com.wuyiccc.tianxuan.pojo.vo.CompanySimpleVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/1/10 22:30
 * saas端公司接口
 */
@RestController
@RequestMapping("/saasCompany")
public class SaasCompanyController {

    @Resource
    private CompanyService companyService;

    @PostMapping("info")
    public CommonResult<CompanySimpleVO> info() {

        // 普通用户
        User user = JWTCurrentUserInterceptor.currentUser.get();

        CompanySimpleVO company = companyService.getCompany(user.getHrInWhichCompanyId());

        return CommonResult.ok(company);
    }

    @PostMapping("moreInfo")
    public CommonResult<CompanyInfoVO> moreInfo() {
        // 普通用户
        User user = JWTCurrentUserInterceptor.currentUser.get();

        CompanyInfoVO companyInfo = companyService.getCompanyInfo(user.getHrInWhichCompanyId());
        return CommonResult.ok(companyInfo);
    }

    @PostMapping("getPhotos")
    public CommonResult<CompanyPhoto> getPhotos() {

        User user = JWTCurrentUserInterceptor.currentUser.get();
        String companyId = user.getHrInWhichCompanyId();
        CompanyPhoto companyPhoto = companyService.getPhotos(companyId);
        return CommonResult.ok(companyPhoto);
    }
}
