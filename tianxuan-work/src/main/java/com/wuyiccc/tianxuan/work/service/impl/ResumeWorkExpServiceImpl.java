package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.db.meta.Column;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.pojo.ResumeWorkExp;
import com.wuyiccc.tianxuan.work.mapper.ResumeWorkExpMapper;
import com.wuyiccc.tianxuan.work.service.ResumeWorkExpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public ResumeWorkExp getWorkExp(String workExpId, String userId) {

        LambdaQueryWrapper<ResumeWorkExp> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeWorkExp::getId, workExpId);
        wrapper.eq(ResumeWorkExp::getUserId, userId);
        List<ResumeWorkExp> resumeWorkExpList = resumeWorkExpMapper.selectList(wrapper);
        if (CollUtil.isEmpty(resumeWorkExpList)) {
            return null;
        }

        return resumeWorkExpList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)

    @Override
    public void delete(String workExpId, String userId) {
        LambdaQueryWrapper<ResumeWorkExp> deleteWrapper = Wrappers.lambdaQuery();
        deleteWrapper.eq(ResumeWorkExp::getId, workExpId);
        deleteWrapper.eq(ResumeWorkExp::getUserId, userId);

        int res = resumeWorkExpMapper.delete(deleteWrapper);
        if (res != 1) {
            throw new CustomException("数据不存在");
        }
    }
}
