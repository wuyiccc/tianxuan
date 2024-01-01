package com.wuyiccc.tianxuan.resource.service;

import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.bo.DataDictionaryBO;

/**
 * @author wuyiccc
 * @date 2024/1/1 10:45
 */
public interface DataDictionaryService {
    void createOrUpdateDataDictionary(DataDictionaryBO dataDictionaryBO);

    PagedGridResult getDataDictListPaged(String typeName, String itemValue, Integer page, Integer limit);
}
