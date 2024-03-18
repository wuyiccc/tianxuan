package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.pojo.ResumeProjectExp;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/3/16 16:32
 */
public interface ResumeProjectExpService {
    List<ResumeProjectExp> findByUserId(String userId, String resumeId);
}
