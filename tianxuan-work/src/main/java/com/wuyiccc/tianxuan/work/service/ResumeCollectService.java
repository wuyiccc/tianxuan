package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.common.result.PagedGridResult;

/**
 * @author wuyiccc
 * @date 2024/6/19 20:49
 */
public interface ResumeCollectService {
    void addCollect(String hrId, String resumeExpectId);

    void removeCollect(String hrId, String resumeExpectId);

    Boolean isHrCollectResume(String hrId, String resumeExpectId);

    Long getCollectResumeCount(String hrUserId);

    PagedGridResult pagedCollectResumeList(String hrId, Integer page, Integer pageSize);

    void saveReadResumeRecord(String hrId, String resumeExpectId);
}
