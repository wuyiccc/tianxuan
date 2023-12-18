package com.wuyiccc.tianxuan.work.service.impl;

import com.wuyiccc.tianxuan.pojo.Resume;
import com.wuyiccc.tianxuan.work.mapper.ResumeMapper;
import com.wuyiccc.tianxuan.work.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2023/12/18 20:33
 */
@Slf4j
@Service
public class ResumeServiceImpl implements ResumeService {

    @Resource
    private ResumeMapper resumeMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initResume(String userId) {

        Resume resume = new Resume();
        resume.setUserId(userId);
        resume.setCreateTime(LocalDateTime.now());
        resume.setUpdatedTime(LocalDateTime.now());

        resumeMapper.insert(resume);
    }
}
