package com.wuyiccc.tianxuan.work.service;

/**
 * @author wuyiccc
 * @date 2024/6/19 20:49
 */
public interface ResumeCollectService {
    void addCollect(String hrId, String resumeExpectId);

    void removeCollect(String hrId, String resumeExpectId);

    Boolean isHrCollectResume(String hrId, String resumeExpectId);

    Long getCollectResumeCount(String hrUserId);
}
