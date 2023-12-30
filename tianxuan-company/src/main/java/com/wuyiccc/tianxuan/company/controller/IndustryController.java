package com.wuyiccc.tianxuan.company.controller;

import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.company.service.IndustryService;
import com.wuyiccc.tianxuan.pojo.Industry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2023/12/30 16:14
 */
@RestController
@RequestMapping("/industry")
public class IndustryController {

    @Resource
    private IndustryService industryService;

    @PostMapping("/createNode")
    public CommonResult<String> createNode(@RequestBody Industry industry) {


        // 1. 判断节点是否已经存
        boolean flag = industryService.getIndustryIsExistByName(industry.getName());
        if (flag) {
            throw new CustomException("该行业已经存在, 请重新命名");
        }

        // 节点创建
        industryService.createIndustry(industry);
        return CommonResult.ok();
    }
}
