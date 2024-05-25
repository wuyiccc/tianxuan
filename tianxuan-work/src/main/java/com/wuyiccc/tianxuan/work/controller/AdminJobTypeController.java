package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.JobType;
import com.wuyiccc.tianxuan.work.service.JobTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/5/25 15:23
 */

@RestController
@RequestMapping("/adminJobType")
@Slf4j
public class AdminJobTypeController {

    @Resource
    private JobTypeService jobTypeService;

    @GetMapping("/getTopList")
    public CommonResult<List<JobType>> getTopList() {

        List<JobType> jobTypeList = jobTypeService.initTopList();
        return CommonResult.ok(jobTypeList);
    }


    @GetMapping("/children/{jobTypeId}")
    public CommonResult<List<JobType>> getChildrenIndustryList(@PathVariable("jobTypeId") String jobTypeId) {

        List<JobType> resList = jobTypeService.getChildrenList(jobTypeId);

        return CommonResult.ok(resList);
    }

    @PostMapping("/createNode")
    public CommonResult<String> createNode(@RequestBody JobType jobType) {

        // 判断节点是否已经存在
        jobTypeService.createNode(jobType);

        return CommonResult.ok();
    }


    /**
     * 更新节点
     *
     * @param jobType
     * @return
     */
    @PostMapping("updateNode")
    public CommonResult<String> updateNode(@RequestBody JobType jobType) {
        jobTypeService.updateNode(jobType);
        return CommonResult.ok();
    }

    @DeleteMapping("deleteNode/{jobTypeId}")
    public CommonResult<String> deleteNode(@PathVariable("jobTypeId") String jobTypeId) {

        jobTypeService.deleteNode(jobTypeId);

        return CommonResult.ok();

    }
}
