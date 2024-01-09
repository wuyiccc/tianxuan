package com.wuyiccc.tianxuan.company.service;

import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Company;
import com.wuyiccc.tianxuan.pojo.bo.CreateCompanyBO;
import com.wuyiccc.tianxuan.pojo.bo.QueryCompanyBO;
import com.wuyiccc.tianxuan.pojo.bo.ReviewCompanyBO;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import com.wuyiccc.tianxuan.pojo.vo.CompanySimpleVO;

/**
 * @author wuyiccc
 * @date 2023/12/24 15:59
 */
public interface CompanyService {

    public Company getByFullName(String fullName);

    String createNewCompany(CreateCompanyBO createCompanyBO);

    String resetNewCompany(CreateCompanyBO createCompanyBO);

    CompanySimpleVO getCompany(String companyId);

    void commitReviewCompanyInfo(ReviewCompanyBO reviewCompanyBO);

    PagedGridResult getCompanyList(QueryCompanyBO queryCompanyBO, Integer page, Integer limit);

    CompanyInfoVO getCompanyInfo(String companyId);

    void updateReviewInfo(ReviewCompanyBO reviewCompanyBO);
}
