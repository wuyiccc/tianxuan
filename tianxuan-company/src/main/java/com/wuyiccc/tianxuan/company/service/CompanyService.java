package com.wuyiccc.tianxuan.company.service;

import com.wuyiccc.tianxuan.pojo.Company;
import com.wuyiccc.tianxuan.pojo.bo.CreateCompanyBO;

/**
 * @author wuyiccc
 * @date 2023/12/24 15:59
 */
public interface CompanyService {

    public Company getByFullName(String fullName);

    String createNewCompany(CreateCompanyBO createCompanyBO);

    String resetNewCompany(CreateCompanyBO createCompanyBO);
}
