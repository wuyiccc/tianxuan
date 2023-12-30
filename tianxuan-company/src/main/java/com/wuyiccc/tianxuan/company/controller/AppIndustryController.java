package com.wuyiccc.tianxuan.company.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.company.service.IndustryService;
import com.wuyiccc.tianxuan.pojo.Industry;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/30 22:55
 * app
 */
@RestController
@RequestMapping("/appIndustry")
public class AppIndustryController {

    @Resource
    private IndustryService industryService;


    @GetMapping("/getTopList")
    public CommonResult<List<Industry>> getTopIndustryList() {
        List<Industry> resList = industryService.getTopIndustryList();
        return CommonResult.ok(resList);
    }


    @GetMapping("/getThirdListByTop/{topIndustryId}")
    public CommonResult<List<Industry>> getThirdIndustryListByTop(@PathVariable("topIndustryId") String topIndustryId) {

        List<Industry> resList = industryService.getThirdIndustryListByTop(topIndustryId);

        return CommonResult.ok(resList);
    }
}
