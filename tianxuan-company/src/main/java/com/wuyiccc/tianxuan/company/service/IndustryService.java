package com.wuyiccc.tianxuan.company.service;

import com.wuyiccc.tianxuan.pojo.Industry;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/30 16:18
 */
public interface IndustryService {
    boolean getIndustryIsExistByName(String name);

    void createIndustry(Industry industry);

    List<Industry> getTopIndustryList();

    List<Industry> getChildrenIndustryList(String industryId);

    void updateNode(Industry industry);
}
