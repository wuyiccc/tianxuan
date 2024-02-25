package com.wuyiccc.tianxuan.resource.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.DataDictionary;
import com.wuyiccc.tianxuan.pojo.bo.QueryDictItemBO;
import com.wuyiccc.tianxuan.pojo.vo.CompanyPointsVO;
import com.wuyiccc.tianxuan.resource.service.DataDictionaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wuyiccc
 * @date 2024/1/1 16:05
 * app端
 */
@RequestMapping("appDataDict")
@RestController
public class AppDataDictController {


    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private DataDictionaryService dataDictionaryService;

    @PostMapping("getDataByCode")
    public CommonResult<List<DataDictionary>> getDataByCode(String typeCode) {

        List<DataDictionary> resList = dataDictionaryService.getDataByCode(typeCode);
        return CommonResult.ok(resList);
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


    @PostMapping("getItemsByKeys2")
    public CommonResult<CompanyPointsVO> getItemsByKeys2(@RequestBody QueryDictItemBO queryDictItemBO) {

        CompanyPointsVO resVO = new CompanyPointsVO();


        List<DataDictionary> dataList = dataDictionaryService.getItemsByKeys(queryDictItemBO.getAdvantage());
        resVO.setAdvantageList(dataList);


        dataList = dataDictionaryService.getItemsByKeys(queryDictItemBO.getSubsidy());
        resVO.setSubsidyList(dataList);


        dataList = dataDictionaryService.getItemsByKeys(queryDictItemBO.getBonus());

        resVO.setBonusList(dataList);


        dataList = dataDictionaryService.getItemsByKeys(queryDictItemBO.getBenefits());
        resVO.setBenefitsList(dataList);


        return CommonResult.ok(resVO);
    }
}
