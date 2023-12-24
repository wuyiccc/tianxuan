package com.wuyiccc.tianxuan.company.service;

import com.wuyiccc.tianxuan.pojo.Company;

/**
 * @author wuyiccc
 * @date 2023/12/24 15:59
 */
public interface CompanyService {

    public Company getByFullName(String fullName);
}
