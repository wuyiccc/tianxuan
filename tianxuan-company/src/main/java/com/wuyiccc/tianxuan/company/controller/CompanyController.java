package com.wuyiccc.tianxuan.company.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.api.feign.UserInfoInnerServiceFeign;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.pojo.Company;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.bo.CreateCompanyBO;
import com.wuyiccc.tianxuan.pojo.bo.ModifyCompanyInfoBO;
import com.wuyiccc.tianxuan.pojo.bo.ReviewCompanyBO;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import com.wuyiccc.tianxuan.pojo.vo.CompanySimpleVO;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
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

        String hrMobile = userCommonResult.getData().getMobile();
        // 保存审核信息, 修改状态为 审核中
        reviewCompanyBO.setHrMobile(hrMobile);

        companyService.commitReviewCompanyInfo(reviewCompanyBO);


        return CommonResult.ok();
    }

    @PostMapping("information")
    public CommonResult<CompanySimpleVO> information(String hrUserId) {

        CommonResult<UserVO> userVOCommonResult = userInfoInnerServiceFeign.get(hrUserId);

        UserVO userVO = userVOCommonResult.getData();

        CompanySimpleVO company = companyService.getCompany(userVO.getHrInWhichCompanyId());
        CompanySimpleVO companySimpleVO = BeanUtil.copyProperties(company, CompanySimpleVO.class);

        return CommonResult.ok(companySimpleVO);
    }


    @PostMapping("moreInfo")
    public CommonResult<CompanyInfoVO> moreInfo(String companyId) {
        // 普通用户
        //User user = JWTCurrentUserInterceptor.currentUser.get();

        CompanyInfoVO companyInfo = companyService.getCompanyInfo(companyId);
        return CommonResult.ok(companyInfo);
    }


    @PostMapping("modify")
    public CommonResult<String> modify(@RequestBody ModifyCompanyInfoBO companyInfoBO) {

        checkUser(companyInfoBO.getCurrentUserId(), companyInfoBO.getCompanyId());

        companyService.modifyCompanyInfo(companyInfoBO);

        return CommonResult.ok();
    }

    private void checkUser(String currentUserId, String companyId) {

        if (CharSequenceUtil.isBlank(currentUserId)) {
            throw new CustomException("公司信息更新失败");
        }

        UserVO hrUser = userInfoInnerServiceFeign.get(currentUserId).getData();
        if (Objects.nonNull(hrUser) && !hrUser.getHrInWhichCompanyId().equalsIgnoreCase(companyId)) {
            throw new CustomException("公司信息更新失败");
        }
    }


}
