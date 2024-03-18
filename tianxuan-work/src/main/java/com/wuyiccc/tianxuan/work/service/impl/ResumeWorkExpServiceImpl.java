package com.wuyiccc.tianxuan.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.pojo.ResumeWorkExp;
import com.wuyiccc.tianxuan.work.mapper.ResumeWorkExpMapper;
import com.wuyiccc.tianxuan.work.service.ResumeWorkExpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/3/16 16:36
 */
@Slf4j
@Service
public class ResumeWorkExpServiceImpl implements ResumeWorkExpService {

    @Resource
    private ResumeWorkExpMapper resumeWorkExpMapper;


    @Override
    public List<ResumeWorkExp> findByUserId(String userId, String resumeId) {

        LambdaQueryWrapper<ResumeWorkExp> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeWorkExp::getUserId, userId);
        wrapper.eq(ResumeWorkExp::getResumeId, resumeId);

        return resumeWorkExpMapper.selectList(wrapper);
    }

    @Override
    public void save(ResumeWorkExp editWorkExpBO) {
        resumeWorkExpMapper.insert(editWorkExpBO);
    }

    @Override
    public void update(ResumeWorkExp entity) {
        resumeWorkExpMapper.updateById(entity);
    }
}
