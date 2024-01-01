package com.wuyiccc.tianxuan.resource.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.DataDictionary;
import com.wuyiccc.tianxuan.resource.service.DataDictionaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/1/1 16:05
 * app端
 */
@RequestMapping("appDataDict")
@RestController
public class AppDataDictController {


    @Resource
    private DataDictionaryService dataDictionaryService;

    @PostMapping("getDataByCode")
    public CommonResult<List<DataDictionary>> getDataByCode(String typeCode) {

        List<DataDictionary> resList = dataDictionaryService.getDataByCode(typeCode);
        return CommonResult.ok(resList);
    }
}
