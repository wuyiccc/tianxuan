package com.wuyiccc.tianxuan.company.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.company.mapper.CompanyMapper;
import com.wuyiccc.tianxuan.company.service.CompanyService;
import com.wuyiccc.tianxuan.pojo.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
