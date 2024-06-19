package com.wuyiccc.tianxuan.resource.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.pojo.Resume;
import com.wuyiccc.tianxuan.pojo.ResumeCollect;
import com.wuyiccc.tianxuan.resource.mapper.ResumeCollectMapper;
import com.wuyiccc.tianxuan.resource.service.ResumeCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/19 20:50
 */
@Slf4j
@Service
public class ResumeCollectServiceImpl implements ResumeCollectService {

    @Resource
    private ResumeCollectMapper resumeCollectMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCollect(String hrId, String resumeExpectId) {


        ResumeCollect resumeCollect = new ResumeCollect();
        resumeCollect.setUserId(hrId);
        resumeCollect.setResumeExpectId(resumeExpectId);

        resumeCollect.setCreateTime(LocalDateTime.now());
        resumeCollect.setUpdatedTime(LocalDateTime.now());

        resumeCollectMapper.insert(resumeCollect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeCollect(String hrId, String resumeExpectId) {

        LambdaQueryWrapper<ResumeCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeCollect::getUserId, hrId);
        wrapper.eq(ResumeCollect::getResumeExpectId, resumeExpectId);

        int res = resumeCollectMapper.delete(wrapper);
        if (res <= 0) {
            throw new CustomException("数据不存在");
        }
    }


    @Override
    public Boolean isHrCollectResume(String hrId, String resumeExpectId) {

        LambdaQueryWrapper<ResumeCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeCollect::getUserId, hrId);
        wrapper.eq(ResumeCollect::getResumeExpectId, resumeExpectId);

        List<ResumeCollect> resumeCollectList = resumeCollectMapper.selectList(wrapper);
        return !CollUtil.isEmpty(resumeCollectList);
    }
}
