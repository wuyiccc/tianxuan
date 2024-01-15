package com.wuyiccc.tianxuan.resource.service;

import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.DataDictionary;
import com.wuyiccc.tianxuan.pojo.bo.DataDictionaryBO;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/1/1 10:45
 */
public interface DataDictionaryService {
    void createOrUpdateDataDictionary(DataDictionaryBO dataDictionaryBO);

    PagedGridResult getDataDictListPaged(String typeName, String itemValue, Integer page, Integer limit);

    DataDictionary getDataDictionary(String dictId);

    void delete(String dictId);

    List<DataDictionary> getDataByCode(String typeCode);

    List<DataDictionary> getItemsByKeys(String[] advantage);
}
