package com.wuyiccc.tianxuan.resource.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.bo.DataDictionaryBO;
import com.wuyiccc.tianxuan.resource.service.DataDictionaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
