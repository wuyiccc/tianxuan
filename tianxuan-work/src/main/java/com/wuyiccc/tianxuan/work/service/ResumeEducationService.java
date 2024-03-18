package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.pojo.ResumeEducation;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/3/16 16:16
 */
public interface ResumeEducationService {
    List<ResumeEducation> findByUserId(String userId, String resumeId);
}
