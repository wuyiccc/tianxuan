package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.common.enumeration.InterviewStatusEnum;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Interview;
import com.wuyiccc.tianxuan.pojo.bo.CreateInterviewBO;

/**
 * @author wuyiccc
 * @date 2024/6/23 13:21
 */
public interface InterviewService {
    String create(CreateInterviewBO createInterviewBO);

    Interview detail(String interviewId, String hrUserId, String companyId);

    void updateStatus(String interviewId, InterviewStatusEnum interviewStatusEnum);

    Long getHrInterviewRecordCount(String hrId);

    Long getCandInterviewRecordCount(String candUserId);

    PagedGridResult pageSearch(String companyId, String hrId, String candUserId, Integer page, Integer limit);
}
