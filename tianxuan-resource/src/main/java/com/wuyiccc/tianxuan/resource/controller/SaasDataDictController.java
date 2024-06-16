package com.wuyiccc.tianxuan.resource.controller;

import com.wuyiccc.tianxuan.common.result.R;
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
 * @date 2024/1/15 20:23
 */
@RestController
@RequestMapping("saasDataDict")
public class SaasDataDictController {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;


    @PostMapping("getItemsByKeys")
    public R<CompanyPointsVO> getItemsByKeys(@RequestBody QueryDictItemBO queryDictItemBO) throws ExecutionException, InterruptedException {

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

        return R.ok(resVO);
    }
}
