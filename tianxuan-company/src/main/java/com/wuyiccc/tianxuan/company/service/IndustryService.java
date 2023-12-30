package com.wuyiccc.tianxuan.company.service;

import com.wuyiccc.tianxuan.pojo.Industry;

/**
 * @author wuyiccc
 * @date 2023/12/30 16:18
 */
public interface IndustryService {
    boolean getIndustryIsExistByName(String name);

    void createIndustry(Industry industry);
}
