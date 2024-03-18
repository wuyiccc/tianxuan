package com.wuyiccc.tianxuan.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.pojo.ResumeProjectExp;
import com.wuyiccc.tianxuan.work.mapper.ResumeProjectExpMapper;
import com.wuyiccc.tianxuan.work.service.ResumeProjectExpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/3/16 16:33
 */
@Slf4j
@Service
public class ResumeProjectExpServiceImpl implements ResumeProjectExpService {


    @Resource
    private ResumeProjectExpMapper resumeProjectExpMapper;

    @Override
    public List<ResumeProjectExp> findByUserId(String userId, String resumeId) {

        LambdaQueryWrapper<ResumeProjectExp> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeProjectExp::getUserId, userId);
        wrapper.eq(ResumeProjectExp::getResumeId, resumeId);
        return resumeProjectExpMapper.selectList(wrapper);
    }
}
