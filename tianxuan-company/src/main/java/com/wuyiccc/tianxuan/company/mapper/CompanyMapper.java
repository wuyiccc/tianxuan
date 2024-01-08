package com.wuyiccc.tianxuan.company.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyiccc.tianxuan.pojo.Company;
import com.wuyiccc.tianxuan.pojo.bo.QueryCompanyBO;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/24 15:54
 */
@Repository
public interface CompanyMapper extends BaseMapper<Company> {

    List<CompanyInfoVO> queryCompanyList(@Param("queryCompanyBO") QueryCompanyBO queryCompanyBO);
}
