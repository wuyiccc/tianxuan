package com.wuyiccc.tianxuan.company.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.api.remote.UserInfoRemoteApi;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.pojo.Company;
import com.wuyiccc.tianxuan.pojo.CompanyPhoto;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.bo.CreateCompanyBO;
import com.wuyiccc.tianxuan.pojo.bo.ModifyCompanyInfoBO;
import com.wuyiccc.tianxuan.pojo.bo.ReviewCompanyBO;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import com.wuyiccc.tianxuan.pojo.vo.CompanySimpleVO;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
import org.springframework.web.bind.annotation.*;

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
    private UserInfoRemoteApi userInfoRemoteApi;

    @PostMapping("/getByFullName")
    public R<CompanySimpleVO> getByFullName(String fullName) {

        if (CharSequenceUtil.isBlank(fullName)) {
            return R.error();
        }

        Company company = companyService.getByFullName(fullName);

        if (Objects.isNull(company)) {
            return R.ok();
        }

        return R.ok(BeanUtil.copyProperties(company, CompanySimpleVO.class));
    }

    @PostMapping("createNewCompany")
    public R<String> createNewCompany(@RequestBody CreateCompanyBO createCompanyBO) {

        String companyId = createCompanyBO.getCompanyId();
        if (CharSequenceUtil.isBlank(companyId)) {
            // 创建公司
            companyId = companyService.createNewCompany(createCompanyBO);
        } else {
            // 修改公司
            companyId = companyService.resetNewCompany(createCompanyBO);
        }

        return R.ok(companyId);
    }

    @PostMapping("getInfo")
    public R<CompanySimpleVO> getInfo(String companyId, boolean withHRCounts) {

        CompanySimpleVO companySimpleVO = companyService.getCompany(companyId);

        if (withHRCounts) {

            R<Long> result = userInfoRemoteApi.getCountsByCompanyId(companyId);
            companySimpleVO.setHrCounts(result.getData());
        }

        return R.ok(companySimpleVO);
    }

    @PostMapping("goReviewCompany")
    public R<String> goReviewCompany(@RequestBody @Valid ReviewCompanyBO reviewCompanyBO) {


        // 微服务调用, 绑定HR企业id
        R<User> userR = userInfoRemoteApi.bindingHRToCompany(reviewCompanyBO.getHrUserId(), reviewCompanyBO.getRealname(), reviewCompanyBO.getCompanyId());

        String hrMobile = userR.getData().getMobile();
        // 保存审核信息, 修改状态为 审核中
        reviewCompanyBO.setHrMobile(hrMobile);

        companyService.commitReviewCompanyInfo(reviewCompanyBO);


        return R.ok();
    }

    @PostMapping("information")
    public R<CompanySimpleVO> information(String hrUserId) {

        R<UserVO> userVOR = userInfoRemoteApi.get(hrUserId);

        UserVO userVO = userVOR.getData();

        CompanySimpleVO company = companyService.getCompany(userVO.getHrInWhichCompanyId());
        CompanySimpleVO companySimpleVO = BeanUtil.copyProperties(company, CompanySimpleVO.class);

        return R.ok(companySimpleVO);
    }


    @PostMapping("moreInfo")
    public R<CompanyInfoVO> moreInfo(String companyId) {
        // 普通用户

        CompanyInfoVO companyInfo = companyService.getCompanyInfo(companyId);
        return R.ok(companyInfo);
    }


    @PostMapping("modify")
    public R<String> modify(@RequestBody ModifyCompanyInfoBO companyInfoBO) {

        checkUser(companyInfoBO.getCurrentUserId(), companyInfoBO.getCompanyId());

        companyService.modifyCompanyInfo(companyInfoBO);

        if (CharSequenceUtil.isNotBlank(companyInfoBO.getPhotos())) {
            companyService.updateCompanyPhoto(companyInfoBO);
        }

        return R.ok();
    }

    private void checkUser(String currentUserId, String companyId) {

        if (CharSequenceUtil.isBlank(currentUserId)) {
            throw new CustomException("公司信息更新失败");
        }

        UserVO hrUser = userInfoRemoteApi.get(currentUserId).getData();
        if (Objects.nonNull(hrUser) && !hrUser.getHrInWhichCompanyId().equalsIgnoreCase(companyId)) {
            throw new CustomException("公司信息更新失败");
        }
    }

    @PostMapping("getPhotos")
    public R<CompanyPhoto> getPhotos(String companyId) {

        CompanyPhoto companyPhoto = companyService.getPhotos(companyId);
        return R.ok(companyPhoto);
    }

    @PostMapping("isVip")
    public R<Boolean> isVip(String companyId) {


        boolean flag = companyService.isVip(companyId);
        return R.ok(flag);
    }
}
