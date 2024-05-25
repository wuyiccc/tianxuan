package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.pojo.JobType;
import com.wuyiccc.tianxuan.pojo.vo.JobTypeSecondAndThirdVO;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/5/25 09:46
 */
public interface JobTypeService {
    List<JobType> initTopList();

    List<JobType> getThirdListByTop(String topJobTypeId);

    List<JobTypeSecondAndThirdVO> getSecondAndThirdListByTop(String topJobTypeId);

    List<JobType> getChildrenList(String jobTypeId);

    void createNode(JobType jobType);

    void updateNode(JobType jobType);

    void deleteNode(String jobTypeId);
}
