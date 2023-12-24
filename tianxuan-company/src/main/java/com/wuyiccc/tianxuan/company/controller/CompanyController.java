package com.wuyiccc.tianxuan.company.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.pojo.Company;
import com.wuyiccc.tianxuan.pojo.vo.CompanySimpleVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/6/19 22:59
 */
@RestController
@RequestMapping("/company")
public class CompanyController {


    @Resource
    private CompanyService companyService;

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
}
