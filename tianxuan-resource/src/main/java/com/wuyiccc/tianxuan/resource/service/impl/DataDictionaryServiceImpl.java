package com.wuyiccc.tianxuan.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.DataDictionary;
import com.wuyiccc.tianxuan.pojo.bo.DataDictionaryBO;
import com.wuyiccc.tianxuan.resource.mapper.DataDictionaryMapper;
import com.wuyiccc.tianxuan.resource.service.DataDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/1/1 10:45
 * admin端
 */
@Slf4j
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrUpdateDataDictionary(DataDictionaryBO dataDictionaryBO) {

        DataDictionary dataDictionary = BeanUtil.copyProperties(dataDictionaryBO, DataDictionary.class);

        if (CharSequenceUtil.isNotBlank(dataDictionary.getId())) {
            dataDictionaryMapper.updateById(dataDictionary);
        } else {

            // 检查是否存在重复的键值对
            LambdaQueryWrapper<DataDictionary> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(DataDictionary::getItemKey, dataDictionary.getItemKey());
            wrapper.eq(DataDictionary::getItemValue, dataDictionary.getItemValue());

            List<DataDictionary> resList = dataDictionaryMapper.selectList(wrapper);
            if (CollUtil.isNotEmpty(resList)) {
                throw new CustomException("字典键值对已经存在");
            }
            dataDictionaryMapper.insert(dataDictionary);
        }
    }

    @Override
    public PagedGridResult getDataDictListPaged(String typeName, String itemValue, Integer page, Integer limit) {

        PageHelper.startPage(page, limit);

        LambdaQueryWrapper<DataDictionary> wrapper = Wrappers.lambdaQuery();
        wrapper.like(DataDictionary::getTypeName, typeName);
        wrapper.like(DataDictionary::getItemValue, itemValue);
        wrapper.orderByAsc(DataDictionary::getTypeCode);

        List<DataDictionary> dataList = dataDictionaryMapper.selectList(wrapper);
        return PagedGridResult.build(dataList, page);
    }

    @Override
    public DataDictionary getDataDictionary(String dictId) {


        return dataDictionaryMapper.selectById(dictId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String dictId) {

        int res = dataDictionaryMapper.deleteById(dictId);
        if (res == 0) {
            throw new CustomException("删除错误");
        }
    }

    @Override
    public List<DataDictionary> getDataByCode(String typeCode) {

        LambdaQueryWrapper<DataDictionary> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DataDictionary::getTypeCode, typeCode);

        return dataDictionaryMapper.selectList(wrapper);
    }
}
