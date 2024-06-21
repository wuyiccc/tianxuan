package com.wuyiccc.tianxuan.work.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.enumeration.ActiveTimeEnum;
import com.wuyiccc.tianxuan.common.enumeration.EduEnum;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.exception.RemoteCallCustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.pojo.ResumeEducation;
import com.wuyiccc.tianxuan.pojo.ResumeExpect;
import com.wuyiccc.tianxuan.pojo.ResumeProjectExp;
import com.wuyiccc.tianxuan.pojo.ResumeWorkExp;
import com.wuyiccc.tianxuan.pojo.bo.*;
import com.wuyiccc.tianxuan.pojo.vo.ResumeVO;
import com.wuyiccc.tianxuan.work.service.ResumeCollectService;
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

    @Resource
    private ResumeCollectService resumeCollectService;


    /**
     * 初始化用户简历
     */
    @PostMapping("init")
    public R<String> init(@RequestParam("userId") String userId) {

        // 远程调用接口重新try-catch
        try {
            resumeService.initResume(userId);
        } catch (Exception e) {
            throw new RemoteCallCustomException(e.getMessage());
        }
        return R.ok();
    }


    @PostMapping("modify")
    public R<String> modify(@RequestBody EditResumeBO editResumeBO) {

        resumeService.modify(editResumeBO);

        return R.ok();
    }

    @PostMapping("/queryMyResume")
    public R<ResumeVO> queryMyResume(String userId) {

        if (CharSequenceUtil.isBlank(userId)) {
            throw new CustomException("用户id不能为空");
        }

        ResumeVO resume = resumeService.queryMyResume(userId);
        return R.ok(resume);
    }

    @PostMapping("/editWorkExp")
    public R<String> editWorkExp(@RequestBody EditWorkExpBO editWorkExpBO) {

        resumeService.editWorkExp(editWorkExpBO);
        return R.ok();
    }

    @PostMapping("/getWorkExp")
    public R<ResumeWorkExp> getWorkExp(String workExpId, String userId) {

        ResumeWorkExp resumeWorkExp = resumeService.getWorkExp(workExpId, userId);
        return R.ok(resumeWorkExp);
    }

    @PostMapping("/deleteWorkExp")
    public R<String> deleteWorkExp(String workExpId, String userId) {


        resumeService.deleteWorkExp(workExpId, userId);

        return R.ok("删除成功");
    }

    @PostMapping("/editProjectExp")
    public R<String> editProjectExp(@RequestBody EditProjectExpBO editProjectExpBO) {

        resumeService.editProjectExp(editProjectExpBO);
        return R.ok();
    }

    @PostMapping("/getProjectExp")
    public R<ResumeProjectExp> getProjectExp(@RequestParam String projectExpId, @RequestParam String userId) {

        ResumeProjectExp resumeProjectExp = resumeService.getProjectExp(projectExpId, userId);
        return R.ok(resumeProjectExp);
    }


    @PostMapping("/deleteProjectExp")
    public R<String> deleteProjectExp(@RequestParam String projectExpId, @RequestParam String userId) {


        resumeService.deleteProjectExp(projectExpId, userId);

        return R.ok();
    }


    @PostMapping("/editEducation")
    public R<String> editEducation(@RequestBody EditEducationBO editEducationBO) {


        resumeService.editEducation(editEducationBO);

        return R.ok();
    }

    @PostMapping("/getEducation")
    public R<ResumeEducation> getEducation(@RequestParam String eduId, @RequestParam String userId) {


        ResumeEducation resumeEducation = resumeService.getEducation(eduId, userId);
        return R.ok(resumeEducation);
    }

    @PostMapping("/deleteEducation")
    public R<String> deleteEducation(@RequestParam String eduId, @RequestParam String userId) {

        resumeService.deleteEducation(eduId, userId);

        return R.ok();
    }

    @PostMapping("/editJobExpect")
    public R<String> editJobExpect(@RequestBody @Valid EditResumeExpectBO editResumeExpectBO) {

        resumeService.editJobExpect(editResumeExpectBO);

        return R.ok();
    }


    @PostMapping("/getMyResumeExpectList")
    public R<List<ResumeExpect>> getMyResumeExpectList(@RequestParam String resumeId, @RequestParam String userId) {

        if (CharSequenceUtil.isBlank(resumeId) || CharSequenceUtil.isBlank(userId)) {
            return R.error();
        }


        List<ResumeExpect> expectList = resumeService.getMyResumeExpectList(resumeId, userId);

        return R.ok(expectList);
    }


    @PostMapping("/deleteMyResumeExpect")
    public R<String> deleteMyResumeExpect(@RequestParam String resumeExpectId, @RequestParam String userId) {

        resumeService.deleteMyResumeExpect(resumeExpectId, userId);

        return R.ok();
    }


    //@SentinelResource(value = "test/refresh", blockHandler = "myBlockHandler", fallback = "myFallbackHandler")
    @PostMapping("/refresh")
    public R<String> refresh(@RequestParam String resumeId, @RequestParam String userId) {

        if (CharSequenceUtil.isBlank(resumeId) || CharSequenceUtil.isBlank(userId)) {
            return R.error();
        }

        String maxCountStr = redisUtils.get(BaseInfoProperties.REDIS_MAX_RESUME_REFRESH_COUNTS);
        Integer maxCount = null;
        if (CharSequenceUtil.isBlank(maxCountStr)) {
            maxCount = 0;
        } else {
            maxCount = Integer.parseInt(maxCountStr);
        }


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

            // 刷入es
            resumeService.transformAndFlushToEs(userId);
        } else {

            return R.errorCustom(ResponseStatusEnum.RESUME_MAX_LIMIT_ERROR);
        }

        return R.ok();
    }

    public R<String> myBlockHandler(String resumeId, String userId, BlockException ex) {

        return R.errorMsg("刷新失败, 请稍后再试");
    }

    /**
     * 异常降级兜底 fallback绑定的方法发生异常之后，就会调用该方法
     */
    public R<String> myFallbackHandler(String resumeId, String userId) {

        return R.errorMsg("myFallbackHandler");
    }

    @PostMapping("/searchResumes")
    public R<PagedGridResult> searchResumes(@RequestBody SearchResumeBO searchResumeBO
            , @RequestParam Integer page
            , @RequestParam Integer limit) {


        String activeTime = searchResumeBO.getActiveTime();

        Integer activeTimes = ActiveTimeEnum.getActiveTimes(activeTime);
        searchResumeBO.setActiveTimes(activeTimes);

        String edu = searchResumeBO.getEdu();

        Integer eduIndex = EduEnum.getEduIndex(edu);
        List<String> eduList = EduEnum.getEduList(eduIndex);
        searchResumeBO.setEduList(eduList);

        PagedGridResult pagedGridResult = resumeService.searchResumes(searchResumeBO, page, limit);

        return R.ok(pagedGridResult);
    }

    @PostMapping("/addCollect")
    public R<String> addCollect(@RequestParam String hrId, @RequestParam String resumeExpectId) {

        // 新增简历收藏
        resumeCollectService.addCollect(hrId, resumeExpectId);

        return R.ok();
    }


    @PostMapping("/removeCollect")
    public R<String> removeCollect(@RequestParam String hrId, @RequestParam String resumeExpectId) {

        resumeCollectService.removeCollect(hrId, resumeExpectId);
        return R.ok();
    }

    @PostMapping("/isHrCollectResume")
    public R<Boolean> isHrCollectResume(@RequestParam String hrId, @RequestParam String resumeExpectId) {

        Boolean flag = resumeCollectService.isHrCollectResume(hrId, resumeExpectId);
        return R.ok(flag);
    }

    @PostMapping("/getCollectResumeCount")
    public R<Long> getCollectResumeCount(@RequestParam String hrId) {

        Long count = resumeCollectService.getCollectResumeCount(hrId);
        return R.ok(count);
    }

    @PostMapping("/pagedCollectResumeList")
    public R<PagedGridResult> pagedCollectResumeList(@RequestParam String hrId, @RequestParam Integer page, @RequestParam Integer pageSize) {


        PagedGridResult res = resumeCollectService.pagedCollectResumeList(hrId, page, pageSize);
        return R.ok(res);
    }

    @PostMapping("/saveReadResumeRecord")
    public R<String> saveReadResumeRecord(@RequestParam String hrId, @RequestParam String resumeExpectId) {

        // 新增简历收藏
        resumeCollectService.saveReadResumeRecord(hrId, resumeExpectId);

        return R.ok();
    }
}
