package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.common.enumeration.InterviewStatusEnum;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.pojo.Interview;
import com.wuyiccc.tianxuan.pojo.bo.CreateInterviewBO;
import com.wuyiccc.tianxuan.work.mapper.InterviewMapper;
import com.wuyiccc.tianxuan.work.service.InterviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public Interview detail(String interviewId, String hrUserId, String companyId) {

        LambdaQueryWrapper<Interview> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Interview::getId, interviewId);
        wrapper.eq(Interview::getHrUserId, hrUserId);
        wrapper.eq(Interview::getCompanyId, companyId);

        List<Interview> interviews = interviewMapper.selectList(wrapper);
        if (CollUtil.isEmpty(interviews)) {
            throw new CustomException("数据不存在");
        }
        return interviews.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(String interviewId, InterviewStatusEnum interviewStatusEnum) {


        Interview interview = new Interview();
        interview.setId(interviewId);
        interview.setStatus(interviewStatusEnum.type);

        interviewMapper.updateById(interview);
    }

    @Override
    public Long getHrInterviewRecordCount(String hrId) {

        LambdaQueryWrapper<Interview> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Interview::getHrUserId, hrId);
        wrapper.notIn(Interview::getStatus, ListUtil.toList(InterviewStatusEnum.CANCEL.type, InterviewStatusEnum.REFUSE.type));

        return interviewMapper.selectCount(wrapper);
    }

    @Override
    public Long getCandInterviewRecordCount(String candUserId) {
        LambdaQueryWrapper<Interview> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Interview::getCandUserId, candUserId);
        wrapper.notIn(Interview::getStatus, ListUtil.toList(InterviewStatusEnum.CANCEL.type, InterviewStatusEnum.REFUSE.type));

        return interviewMapper.selectCount(wrapper);
    }
}
