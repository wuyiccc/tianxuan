package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.bo.EditJobBO;
import com.wuyiccc.tianxuan.pojo.bo.SearchJobsBO;

/**
 * @author wuyiccc
 * @date 2024/5/27 22:40
 */
public interface JobService {
    void modifyJobDetail(EditJobBO editJobBO);

    PagedGridResult queryJobList(String hrId, String companyId, Integer page, Integer limit, Integer status);

    Job queryJobDetail(String hrId, String companyId, String jobId);

    void modifyStatus(String hrId, String companyId, String jobId, Integer jobStatus);

    PagedGridResult searchJobs(SearchJobsBO searchJobsBO, Integer page, Integer limit);

    Long countJob(String hrId);

    Long getCollecJobCount(String candUserId);
}
