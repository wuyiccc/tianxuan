package com.wuyiccc.tianxuan.work.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.exception.RemoteCallCustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.bo.EditResumeBO;
import com.wuyiccc.tianxuan.pojo.vo.ResumeVO;
import com.wuyiccc.tianxuan.work.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2023/12/18 20:35
 * 内部接口调用
 * app
 */
@RestController
@RequestMapping("/resume")
@Slf4j
public class ResumeController {


    @Resource
    private ResumeService resumeService;

    /**
     * 初始化用户简历
     */
    @PostMapping("init")
    public CommonResult<String> init(@RequestParam("userId") String userId) {

        // 远程调用接口重新try-catch
        try {
            resumeService.initResume(userId);
        } catch (Exception e) {
            throw new RemoteCallCustomException(e.getMessage());
        }
        return CommonResult.ok();
    }


    @PostMapping("modify")
    public CommonResult<String> modify(@RequestBody EditResumeBO editResumeBO) {

        resumeService.modify(editResumeBO);

        return CommonResult.ok();
    }

    @PostMapping("/queryMyResume")
    public CommonResult<ResumeVO> queryMyResume(String userId) {

        if (CharSequenceUtil.isBlank(userId)) {
            throw new CustomException("用户id不能为空");
        }

        ResumeVO resume = resumeService.queryMyResume(userId);
        return CommonResult.ok(resume);
    }

}
