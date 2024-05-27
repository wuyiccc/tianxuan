package com.wuyiccc.tianxuan.work.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.bo.EditJobBO;
import com.wuyiccc.tianxuan.work.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author wuyiccc
 * @date 2024/5/27 22:31
 */
@RestController
@RequestMapping("/appJob")
@Slf4j
public class AppJobController {


    @Resource
    private JobService jobService;



    @PostMapping("/modify")
    public CommonResult<String> modify(@RequestBody @Valid EditJobBO editJobBO) {

        jobService.modifyJobDetail(editJobBO);

        return CommonResult.ok();
    }

    @PostMapping("hr/jobList")
    public CommonResult<PagedGridResult> jobListHR(String hrId,
                                                        String companyId,
                                                        Integer page,
                                                        Integer limit,
                                                        Integer status) {

        if (StringUtils.isBlank(hrId)) {
            return CommonResult.errorMsg("hrId 不能为空");
        }

        if (page == null) page = 1;
        if (limit == null) limit = 10;

        PagedGridResult gridResult = jobService.queryJobList(hrId,
                companyId,
                page,
                limit,
                status);

        return CommonResult.ok(gridResult);
    }
}
