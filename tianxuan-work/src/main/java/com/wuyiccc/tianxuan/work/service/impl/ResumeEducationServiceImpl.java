package com.wuyiccc.tianxuan.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.pojo.ResumeEducation;
import com.wuyiccc.tianxuan.work.mapper.ResumeEducationMapper;
import com.wuyiccc.tianxuan.work.service.ResumeEducationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/3/16 16:16
 */
@Service
@Slf4j
public class ResumeEducationServiceImpl implements ResumeEducationService {


    @Resource
    private ResumeEducationMapper resumeEducationMapper;


    @Override
    public List<ResumeEducation> findByUserId(String userId, String resumeId) {

        LambdaQueryWrapper<ResumeEducation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeEducation::getUserId, userId);
        wrapper.eq(ResumeEducation::getResumeId, resumeId);

        return resumeEducationMapper.selectList(wrapper);
    }
}
