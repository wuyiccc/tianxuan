package com.wuyiccc.tianxuan.resource.controller;

import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.DataDictionary;
import com.wuyiccc.tianxuan.pojo.bo.DataDictionaryBO;
import com.wuyiccc.tianxuan.pojo.bo.QueryDictItemBO;
import com.wuyiccc.tianxuan.pojo.vo.CompanyPointsVO;
import com.wuyiccc.tianxuan.resource.service.DataDictionaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wuyiccc
 * @date 2024/1/1 10:23
 */
@RestController
@RequestMapping("/dataDict")
public class DataDictController {


    @Resource
    private DataDictionaryService dataDictionaryService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

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

    @PostMapping("modify")
    public CommonResult<String> modify(@RequestBody DataDictionaryBO dataDictionaryBO) {

        if (Objects.isNull(dataDictionaryBO.getId())) {
            throw new CustomException("修改的数据字典id不能为空");
        }

        dataDictionaryService.createOrUpdateDataDictionary(dataDictionaryBO);
        return CommonResult.ok();
    }

    @PostMapping("item")
    public CommonResult<DataDictionary> item(String dictId) {

        DataDictionary dataDictionary = dataDictionaryService.getDataDictionary(dictId);
        return CommonResult.ok(dataDictionary);
    }


    @PostMapping("delete")
    public CommonResult<String> delete(String dictId) {

        dataDictionaryService.delete(dictId);
        return CommonResult.ok();
    }

    @PostMapping("getItemsByKeys")
    public CommonResult<CompanyPointsVO> getItemsByKeys(@RequestBody QueryDictItemBO queryDictItemBO) throws ExecutionException, InterruptedException {

        CompanyPointsVO resVO = new CompanyPointsVO();


        CompletableFuture<List<DataDictionary>> advantageFuture = CompletableFuture.supplyAsync(() -> {

            List<DataDictionary> dataList = dataDictionaryService.getItemsByKeys(queryDictItemBO.getAdvantage());
            resVO.setAdvantageList(dataList);
            return dataList;
        }, threadPoolExecutor);


        CompletableFuture<List<DataDictionary>> subsidyFuture = CompletableFuture.supplyAsync(() -> {
            List<DataDictionary> dataList = dataDictionaryService.getItemsByKeys(queryDictItemBO.getSubsidy());
            resVO.setSubsidyList(dataList);
            return dataList;
        }, threadPoolExecutor);


        CompletableFuture<List<DataDictionary>> bonusFuture = CompletableFuture.supplyAsync(() -> {
            List<DataDictionary> dataList = dataDictionaryService.getItemsByKeys(queryDictItemBO.getBonus());

            resVO.setBonusList(dataList);
            return dataList;

        }, threadPoolExecutor);


        CompletableFuture<List<DataDictionary>> benefitsFuture = CompletableFuture.supplyAsync(() -> {
            List<DataDictionary> dataList = dataDictionaryService.getItemsByKeys(queryDictItemBO.getBenefits());
            resVO.setBenefitsList(dataList);
            return dataList;
        }, threadPoolExecutor);

        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(advantageFuture, subsidyFuture, bonusFuture, benefitsFuture);

        allOfFuture.get();

        return CommonResult.ok(resVO);
    }
}
