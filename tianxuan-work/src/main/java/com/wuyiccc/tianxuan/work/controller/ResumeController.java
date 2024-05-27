package com.wuyiccc.tianxuan.work.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.exception.RemoteCallCustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.pojo.ResumeEducation;
import com.wuyiccc.tianxuan.pojo.ResumeExpect;
import com.wuyiccc.tianxuan.pojo.ResumeProjectExp;
import com.wuyiccc.tianxuan.pojo.ResumeWorkExp;
import com.wuyiccc.tianxuan.pojo.bo.*;
import com.wuyiccc.tianxuan.pojo.vo.ResumeVO;
import com.wuyiccc.tianxuan.work.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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

    @Resource
    private RedisUtils redisUtils;


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

    @PostMapping("/editWorkExp")
    public CommonResult<String> editWorkExp(@RequestBody EditWorkExpBO editWorkExpBO) {

        resumeService.editWorkExp(editWorkExpBO);
        return CommonResult.ok();
    }

    @PostMapping("/getWorkExp")
    public CommonResult<ResumeWorkExp> getWorkExp(String workExpId, String userId) {

        ResumeWorkExp resumeWorkExp = resumeService.getWorkExp(workExpId, userId);
        return CommonResult.ok(resumeWorkExp);
    }

    @PostMapping("/deleteWorkExp")
    public CommonResult<String> deleteWorkExp(String workExpId, String userId) {


        resumeService.deleteWorkExp(workExpId, userId);

        return CommonResult.ok("删除成功");
    }

    @PostMapping("/editProjectExp")
    public CommonResult<String> editProjectExp(@RequestBody EditProjectExpBO editProjectExpBO) {

        resumeService.editProjectExp(editProjectExpBO);
        return CommonResult.ok();
    }

    @PostMapping("/getProjectExp")
    public CommonResult<ResumeProjectExp> getProjectExp(@RequestParam String projectExpId, @RequestParam String userId) {

        ResumeProjectExp resumeProjectExp = resumeService.getProjectExp(projectExpId, userId);
        return CommonResult.ok(resumeProjectExp);
    }


    @PostMapping("/deleteProjectExp")
    public CommonResult<String> deleteProjectExp(@RequestParam String projectExpId, @RequestParam String userId) {


        resumeService.deleteProjectExp(projectExpId, userId);

        return CommonResult.ok();
    }


    @PostMapping("/editEducation")
    public CommonResult<String> editEducation(@RequestBody EditEducationBO editEducationBO) {


        resumeService.editEducation(editEducationBO);

        return CommonResult.ok();
    }

    @PostMapping("/getEducation")
    public CommonResult<ResumeEducation> getEducation(@RequestParam String eduId, @RequestParam String userId) {


        ResumeEducation resumeEducation = resumeService.getEducation(eduId, userId);
        return CommonResult.ok(resumeEducation);
    }

    @PostMapping("/deleteEducation")
    public CommonResult<String> deleteEducation(@RequestParam String eduId, @RequestParam String userId) {

        resumeService.deleteEducation(eduId, userId);

        return CommonResult.ok();
    }

    @PostMapping("/editJobExpect")
    public CommonResult<String> editJobExpect(@RequestBody @Valid EditResumeExpectBO editResumeExpectBO) {

        resumeService.editJobExpect(editResumeExpectBO);

        return CommonResult.ok();
    }


    @PostMapping("/getMyResumeExpectList")
    public CommonResult<List<ResumeExpect>> getMyResumeExpectList(@RequestParam String resumeId, @RequestParam String userId) {

        if (CharSequenceUtil.isBlank(resumeId) || CharSequenceUtil.isBlank(userId)) {
            return CommonResult.error();
        }


        List<ResumeExpect> expectList = resumeService.getMyResumeExpectList(resumeId, userId);

        return CommonResult.ok(expectList);
    }


    @PostMapping("/deleteMyResumeExpect")
    public CommonResult<String> deleteMyResumeExpect(@RequestParam String resumeExpectId, @RequestParam String userId) {

        resumeService.deleteMyResumeExpect(resumeExpectId, userId);

        return CommonResult.ok();
    }


    @PostMapping("/refresh")
    public CommonResult<String> refresh(@RequestParam String resumeId, @RequestParam String userId) {

        if (CharSequenceUtil.isBlank(resumeId) || CharSequenceUtil.isBlank(userId)) {
            return CommonResult.error();
        }

        Integer maxCount = 3;

        String today = LocalDateUtils.getLocalDateStr();
        int userAlreadyRefreshCount = 0;

        String redisKey = BaseInfoProperties.USER_ALREADY_REFRESHED_COUNTS
                + StrPool.COLON + today
                + StrPool.COLON + userId;
        String userAlreadyRefreshedCountStr = redisUtils.get(redisKey);
        if (CharSequenceUtil.isBlank(userAlreadyRefreshedCountStr)) {
            redisUtils.set(redisKey, String.valueOf(userAlreadyRefreshCount), 24 * 60 * 60);
        } else {
            userAlreadyRefreshCount = Integer.parseInt(userAlreadyRefreshedCountStr);
        }

        if (userAlreadyRefreshCount < maxCount) {

            // 刷新简历
            resumeService.refreshResume(resumeId, userId);

            // 自增
            redisUtils.increment(redisKey, 1);
        } else {

            return CommonResult.errorCustom(ResponseStatusEnum.RESUME_MAX_LIMIT_ERROR);
        }

        return CommonResult.ok();
    }
}
