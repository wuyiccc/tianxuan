package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.result.R;
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
    public R<List<JobType>> initTopList() {

        List<JobType> resList = jobTypeService.initTopList();
        return R.ok(resList);
    }

    @GetMapping("/getThirdListByTop/{topJobTypeId}")
    public R<List<JobType>> getThirdListByTop(@PathVariable("topJobTypeId") String topJobTypeId) {

        List<JobType> resList = jobTypeService.getThirdListByTop(topJobTypeId);
        return R.ok(resList);
    }


    @GetMapping("/getSecondAndThirdListByTop/{topJobTypeId}")
    public R<List<JobTypeSecondAndThirdVO>> getSecondAndThirdListByTop(@PathVariable("topJobTypeId") String topJobTypeId) {

        List<JobTypeSecondAndThirdVO> resList = jobTypeService.getSecondAndThirdListByTop(topJobTypeId);
        return R.ok(resList);
    }

}
