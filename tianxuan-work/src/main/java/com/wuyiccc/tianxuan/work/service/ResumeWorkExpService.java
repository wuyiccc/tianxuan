package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.pojo.ResumeWorkExp;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/3/16 16:36
 */
public interface ResumeWorkExpService {
    List<ResumeWorkExp> findByUserId(String userId);

    void save(ResumeWorkExp editWorkExpBO);

    void update(ResumeWorkExp entity);
}
