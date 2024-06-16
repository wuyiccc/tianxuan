package com.wuyiccc.tianxuan.company.controller;

import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.result.R;
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
    public R<CompanySimpleVO> info() {

        // 普通用户
        User user = JWTCurrentUserInterceptor.currentUser.get();

        CompanySimpleVO company = companyService.getCompany(user.getHrInWhichCompanyId());

        return R.ok(company);
    }

    @PostMapping("moreInfo")
    public R<CompanyInfoVO> moreInfo() {
        // 普通用户
        User user = JWTCurrentUserInterceptor.currentUser.get();

        CompanyInfoVO companyInfo = companyService.getCompanyInfo(user.getHrInWhichCompanyId());
        return R.ok(companyInfo);
    }

    @PostMapping("getPhotos")
    public R<CompanyPhoto> getPhotos() {

        User user = JWTCurrentUserInterceptor.currentUser.get();
        String companyId = user.getHrInWhichCompanyId();
        CompanyPhoto companyPhoto = companyService.getPhotos(companyId);
        return R.ok(companyPhoto);
    }

    @PostMapping("isVip")
    public R<Boolean> isVip() {

        User user = JWTCurrentUserInterceptor.currentUser.get();

        boolean flag = companyService.isVip(user.getHrInWhichCompanyId());
        return R.ok(flag);
    }
}
