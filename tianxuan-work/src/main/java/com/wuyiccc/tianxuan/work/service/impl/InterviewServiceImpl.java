package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.wuyiccc.tianxuan.common.enumeration.InterviewStatusEnum;
import com.wuyiccc.tianxuan.pojo.Interview;
import com.wuyiccc.tianxuan.pojo.bo.CreateInterviewBO;
import com.wuyiccc.tianxuan.work.mapper.InterviewMapper;
import com.wuyiccc.tianxuan.work.service.InterviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/23 13:21
 */
@Slf4j
@Service
public class InterviewServiceImpl implements InterviewService {

    @Resource
    private InterviewMapper interviewMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateInterviewBO createInterviewBO) {

        Interview interview = BeanUtil.copyProperties(createInterviewBO, Interview.class);
        interview.setStatus(InterviewStatusEnum.WAITING.type);
        interviewMapper.insert(interview);
        return interview.getId();
    }
}
