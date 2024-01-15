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

/**
 * @author wuyiccc
 * @date 2024/1/15 20:23
 */
@RestController
@RequestMapping("saasDataDict")
public class SaasDataDictController {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @PostMapping("getItemsByKeys")
    public CommonResult<CompanyPointsVO> getItemsByKeys(@RequestBody QueryDictItemBO queryDictItemBO) {

        CompanyPointsVO resVO = new CompanyPointsVO();

        List<DataDictionary> advantages = dataDictionaryService.getItemsByKeys(queryDictItemBO.getAdvantage());
        resVO.setAdvantageList(advantages);

        List<DataDictionary> subsidy = dataDictionaryService.getItemsByKeys(queryDictItemBO.getSubsidy());
        resVO.setSubsidyList(subsidy);

        List<DataDictionary> bonus = dataDictionaryService.getItemsByKeys(queryDictItemBO.getBonus());
        resVO.setBonusList(bonus);

        List<DataDictionary> benefits = dataDictionaryService.getItemsByKeys(queryDictItemBO.getBenefits());
        resVO.setBenefitsList(benefits);

        return CommonResult.ok(resVO);
    }
}
