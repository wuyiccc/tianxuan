package com.wuyiccc.tianxuan.resource.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.bo.DataDictionaryBO;
import com.wuyiccc.tianxuan.resource.service.DataDictionaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/1/1 10:23
 */
@RestController
@RequestMapping("/dataDict")
public class DataDictController {


    @Resource
    private DataDictionaryService dataDictionaryService;

    @PostMapping("create")
    public CommonResult<String> create(@RequestBody DataDictionaryBO dataDictionaryBO) {

        dataDictionaryService.createOrUpdateDataDictionary(dataDictionaryBO);

        return CommonResult.ok();
    }

    @PostMapping("list")
    public CommonResult<PagedGridResult> list(String typeName, String itemValue, Integer page, Integer limit) {

        if (Objects.isNull(page)) {
            page = 1;
        }
        if (Objects.isNull(limit)) {
            limit = 10;
        }

        PagedGridResult pagedGridResult = dataDictionaryService.getDataDictListPaged(typeName, itemValue, page, limit);
        return CommonResult.ok(pagedGridResult);
    }


}