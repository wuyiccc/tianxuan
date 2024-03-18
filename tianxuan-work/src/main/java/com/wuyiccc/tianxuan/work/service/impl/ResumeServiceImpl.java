package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.pojo.Resume;
import com.wuyiccc.tianxuan.pojo.ResumeEducation;
import com.wuyiccc.tianxuan.pojo.ResumeProjectExp;
import com.wuyiccc.tianxuan.pojo.ResumeWorkExp;
import com.wuyiccc.tianxuan.pojo.bo.EditResumeBO;
import com.wuyiccc.tianxuan.pojo.bo.EditWorkExpBO;
import com.wuyiccc.tianxuan.pojo.vo.ResumeVO;
import com.wuyiccc.tianxuan.work.mapper.ResumeMapper;
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
}
