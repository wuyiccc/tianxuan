package com.wuyiccc.tianxuan.company.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.common.enumeration.CompanyReviewStatusEnum;
import com.wuyiccc.tianxuan.common.enumeration.YesOrNoEnum;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import com.wuyiccc.tianxuan.company.mapper.CompanyMapper;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.pojo.Company;
import com.wuyiccc.tianxuan.pojo.bo.CreateCompanyBO;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/24 15:59
 */
@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {


    @Resource
    private CompanyMapper companyMapper;

    @Override
    public Company getByFullName(String fullName) {

        LambdaQueryWrapper<Company> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Company::getCompanyName, fullName);

        List<Company> companyList = companyMapper.selectList(wrapper);

        if (CollUtil.isEmpty(companyList)) {
            return null;
        }

        return companyList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createNewCompany(CreateCompanyBO createCompanyBO) {

        Company newCompany = new Company();
        BeanUtil.copyProperties(createCompanyBO, newCompany);
        newCompany.setIsVip(YesOrNoEnum.NO.type);
        newCompany.setReviewStatus(CompanyReviewStatusEnum.NOTHING.type);
        newCompany.setCreatedTime(LocalDateTime.now());
        newCompany.setUpdatedTime(LocalDateTime.now());

        companyMapper.insert(newCompany);

        return newCompany.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String resetNewCompany(CreateCompanyBO createCompanyBO) {

        Company newCompany = BeanUtil.copyProperties(createCompanyBO, Company.class);
        newCompany.setId(createCompanyBO.getCompanyId());
        newCompany.setReviewStatus(CompanyReviewStatusEnum.NOTHING.type);
        newCompany.setUpdatedTime(LocalDateTime.now());

        companyMapper.updateById(newCompany);
        return newCompany.getId();
    }
}
