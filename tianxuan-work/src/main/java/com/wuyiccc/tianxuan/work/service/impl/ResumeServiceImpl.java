package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.*;
import com.wuyiccc.tianxuan.pojo.bo.*;
import com.wuyiccc.tianxuan.pojo.dto.SearchResumeDTO;
import com.wuyiccc.tianxuan.pojo.vo.ResumeVO;
import com.wuyiccc.tianxuan.pojo.vo.SearchResumeVO;
import com.wuyiccc.tianxuan.work.mapper.ResumeEducationMapper;
import com.wuyiccc.tianxuan.work.mapper.ResumeExpectMapper;
import com.wuyiccc.tianxuan.work.mapper.ResumeMapper;
import com.wuyiccc.tianxuan.work.mapper.ResumeProjectExpMapper;
import com.wuyiccc.tianxuan.work.service.ResumeEducationService;
import com.wuyiccc.tianxuan.work.service.ResumeProjectExpService;
import com.wuyiccc.tianxuan.work.service.ResumeService;
import com.wuyiccc.tianxuan.work.service.ResumeWorkExpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/18 20:33
 */
@Slf4j
@Service
public class ResumeServiceImpl implements ResumeService {

    @Resource
    private ResumeMapper resumeMapper;


    @Resource
    private ResumeEducationService resumeEducationService;

    @Resource
    private ResumeProjectExpService resumeProjectExpService;


    @Resource
    private ResumeWorkExpService resumeWorkExpService;

    @Resource
    private ResumeProjectExpMapper resumeProjectExpMapper;

    @Resource
    private ResumeEducationMapper resumeEducationMapper;

    @Resource
    private ResumeExpectMapper resumeExpectMapper;




    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initResume(String userId) {

        Resume resume = new Resume();
        resume.setUserId(userId);
        resume.setCreateTime(LocalDateTime.now());
        resume.setUpdatedTime(LocalDateTime.now());

        resumeMapper.insert(resume);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(EditResumeBO editResumeBO) {

        Resume resume = new Resume();
        BeanUtil.copyProperties(editResumeBO, resume);

        resume.setUpdatedTime(LocalDateTime.now());

        LambdaQueryWrapper<Resume> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Resume::getId, editResumeBO.getId());
        // 补充校验, 拿到当前登录人的id, 防止其他用户篡改
        wrapper.eq(Resume::getUserId, editResumeBO.getUserId());

        int res = resumeMapper.update(resume, wrapper);
        if (res != 1) {
            throw new CustomException("简历不存在");
        }
    }

    @Override
    public ResumeVO queryMyResume(String userId) {

        LambdaQueryWrapper<Resume> resumeWrapper = Wrappers.lambdaQuery();
        resumeWrapper.eq(Resume::getUserId, userId);
        List<Resume> resumeList = resumeMapper.selectList(resumeWrapper);
        if (CollUtil.isEmpty(resumeList)) {
            throw new CustomException("简历不存在");
        }

        Resume resume = resumeList.get(0);

        ResumeVO vo = BeanUtil.copyProperties(resume, ResumeVO.class);

        List<ResumeEducation> resumeEducationList = resumeEducationService.findByUserId(userId, resume.getId());

        vo.setEducationList(resumeEducationList);


        List<ResumeProjectExp> resumeProjectExpList = resumeProjectExpService.findByUserId(userId, resume.getId());
        vo.setProjectExpList(resumeProjectExpList);

        List<ResumeWorkExp> resumeWorkExpList = resumeWorkExpService.findByUserId(userId, resume.getId());

        vo.setWorkExpList(resumeWorkExpList);

        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editWorkExp(EditWorkExpBO editWorkExpBO) {


        ResumeWorkExp entity = BeanUtil.copyProperties(editWorkExpBO, ResumeWorkExp.class);
        entity.setUpdatedTime(LocalDateTime.now());
        if (CharSequenceUtil.isBlank(editWorkExpBO.getId())) {
            entity.setCreateTime(LocalDateTime.now());
            resumeWorkExpService.save(entity);
            return;
        }

        resumeWorkExpService.update(entity);

    }

    @Override
    public ResumeWorkExp getWorkExp(String workExpId, String userId) {

        ResumeWorkExp workExp = resumeWorkExpService.getWorkExp(workExpId, userId);
        return workExp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWorkExp(String workExpId, String userId) {

        resumeWorkExpService.delete(workExpId, userId);


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editProjectExp(EditProjectExpBO editProjectExpBO) {


        ResumeProjectExp resumeProjectExp = new ResumeProjectExp();
        BeanUtil.copyProperties(editProjectExpBO, resumeProjectExp);

        resumeProjectExp.setUpdatedTime(LocalDateTime.now());

        if (StrUtil.isBlank(resumeProjectExp.getId())) {
            resumeProjectExp.setCreateTime(LocalDateTime.now());
            resumeProjectExpMapper.insert(resumeProjectExp);
        } else {
            LambdaQueryWrapper<ResumeProjectExp> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ResumeProjectExp::getId, resumeProjectExp.getId());
            wrapper.eq(ResumeProjectExp::getUserId, resumeProjectExp.getUserId());
            wrapper.eq(ResumeProjectExp::getResumeId, resumeProjectExp.getResumeId());

            resumeProjectExpMapper.update(resumeProjectExp, wrapper);
        }

    }

    @Override
    public ResumeProjectExp getProjectExp(String projectExpId, String userId) {

        LambdaQueryWrapper<ResumeProjectExp> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeProjectExp::getId, projectExpId);
        wrapper.eq(ResumeProjectExp::getUserId, userId);

        List<ResumeProjectExp> resumeProjectExpList = resumeProjectExpMapper.selectList(wrapper);
        if (CollUtil.isEmpty(resumeProjectExpList)) {
            return null;
        }

        return resumeProjectExpList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProjectExp(String projectExpId, String userId) {

        LambdaQueryWrapper<ResumeProjectExp> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeProjectExp::getId, projectExpId);
        wrapper.eq(ResumeProjectExp::getUserId, userId);

        int res = resumeProjectExpMapper.delete(wrapper);
        if (res != 1) {
            throw new CustomException("删除失败");
        }
    }

    @Override
    public void editEducation(EditEducationBO editEducationBO) {


        ResumeEducation resumeEducation = new ResumeEducation();
        BeanUtil.copyProperties(editEducationBO, resumeEducation);

        resumeEducation.setUpdatedTime(LocalDateTime.now());

        if (StrUtil.isBlank(resumeEducation.getId())) {
            resumeEducation.setCreateTime(LocalDateTime.now());
            resumeEducationMapper.insert(resumeEducation);
        } else {
            LambdaQueryWrapper<ResumeEducation> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ResumeEducation::getId, resumeEducation.getId());
            wrapper.eq(ResumeEducation::getUserId, resumeEducation.getUserId());
            wrapper.eq(ResumeEducation::getResumeId, resumeEducation.getResumeId());

            resumeEducationMapper.update(resumeEducation, wrapper);
        }

    }

    @Override
    public ResumeEducation getEducation(String eduId, String userId) {


        LambdaQueryWrapper<ResumeEducation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeEducation::getId, eduId);
        wrapper.eq(ResumeEducation::getUserId, userId);

        List<ResumeEducation> resumeEducationList = resumeEducationMapper.selectList(wrapper);
        if (CollUtil.isEmpty(resumeEducationList)) {
            return null;
        }

        return resumeEducationList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteEducation(String eduId, String userId) {


        LambdaQueryWrapper<ResumeEducation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeEducation::getId, eduId);
        wrapper.eq(ResumeEducation::getUserId, userId);

        int res = resumeEducationMapper.delete(wrapper);
        if (res != 1) {
            throw new CustomException("删除失败");
        }
    }

    @Override
    public void editJobExpect(EditResumeExpectBO editResumeExpectBO) {

        ResumeExpect resumeExpect = new ResumeExpect();
        BeanUtil.copyProperties(editResumeExpectBO, resumeExpect);

        resumeExpect.setUpdatedTime(LocalDateTime.now());

        if (CharSequenceUtil.isBlank(editResumeExpectBO.getId())) {
            resumeExpect.setCreateTime(LocalDateTime.now());
            resumeExpectMapper.insert(resumeExpect);
        } else {
            LambdaQueryWrapper<ResumeExpect> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ResumeExpect::getId, editResumeExpectBO.getId());
            wrapper.eq(ResumeExpect::getUserId, editResumeExpectBO.getUserId());
            wrapper.eq(ResumeExpect::getResumeId, editResumeExpectBO.getResumeId());
            resumeExpectMapper.update(resumeExpect, wrapper);
        }
    }

    @Override
    public List<ResumeExpect> getMyResumeExpectList(String resumeId, String userId) {

        LambdaQueryWrapper<ResumeExpect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeExpect::getResumeId, resumeId);
        wrapper.eq(ResumeExpect::getUserId, userId);
        wrapper.orderByDesc(ResumeExpect::getUpdatedTime);

        return resumeExpectMapper.selectList(wrapper);
    }

    @Override
    public void deleteMyResumeExpect(String resumeExpectId, String userId) {

        LambdaQueryWrapper<ResumeExpect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeExpect::getId, resumeExpectId);
        wrapper.eq(ResumeExpect::getUserId, userId);

        int res = resumeExpectMapper.delete(wrapper);

        if (res != 1) {
            throw new CustomException("删除失败");
        }
    }

    @Override
    public void refreshResume(String resumeId, String userId) {


        EditResumeBO editResumeBO = new EditResumeBO();
        editResumeBO.setId(resumeId);
        editResumeBO.setUserId(userId);
        editResumeBO.setRefreshTime(LocalDateTime.now());

        this.modify(editResumeBO);
    }

    @Override
    public PagedGridResult searchResumes(SearchResumeBO searchResumeBO, Integer page, Integer limit) {


        String basicTitle = searchResumeBO.getBasicTitle();
        String jobType = searchResumeBO.getJobType();
        Integer beginAge = searchResumeBO.getBeginAge();
        Integer endAge = searchResumeBO.getEndAge();
        Integer sex = searchResumeBO.getSex();
        Integer activeTimes = searchResumeBO.getActiveTimes();
        Integer beginWorkExpYears = searchResumeBO.getBeginWorkExpYears();
        Integer endWorkExpYears = searchResumeBO.getEndWorkExpYears();
        String edu = searchResumeBO.getEdu();
        List<String> eduList = searchResumeBO.getEduList();
        Integer beginSalary = searchResumeBO.getBeginSalary();
        Integer endSalary = searchResumeBO.getEndSalary();
        String jobStatus = searchResumeBO.getJobStatus();


        SearchResumeDTO queryDTO = BeanUtil.copyProperties(searchResumeBO, SearchResumeDTO.class);

        PageHelper.startPage(page, limit);
        List<SearchResumeVO> voList = resumeMapper.searchResumes(queryDTO);

        return PagedGridResult.build(voList, page);
    }

}
