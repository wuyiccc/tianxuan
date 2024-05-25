package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.JobType;
import com.wuyiccc.tianxuan.pojo.vo.JobTypeSecondAndThirdVO;
import com.wuyiccc.tianxuan.work.service.JobTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/5/25 09:44
 * app调用
 */
@RestController
@RequestMapping("/appJobType")
@Slf4j
public class AppJobTypeController {

    @Resource
    private JobTypeService jobTypeService;

    @GetMapping("/initTopList")
    public CommonResult<List<JobType>> initTopList() {

        List<JobType> resList = jobTypeService.initTopList();
        return CommonResult.ok(resList);
    }

    @GetMapping("/getThirdListByTop/{topJobTypeId}")
    public CommonResult<List<JobType>> getThirdListByTop(@PathVariable("topJobTypeId") String topJobTypeId) {

        List<JobType> resList = jobTypeService.getThirdListByTop(topJobTypeId);
        return CommonResult.ok(resList);
    }


    @GetMapping("/getSecondAndThirdListByTop/{topJobTypeId}")
    public CommonResult<List<JobTypeSecondAndThirdVO>> getSecondAndThirdListByTop(@PathVariable("topJobTypeId") String topJobTypeId) {

        List<JobTypeSecondAndThirdVO> resList = jobTypeService.getSecondAndThirdListByTop(topJobTypeId);
        return CommonResult.ok(resList);
    }

}
