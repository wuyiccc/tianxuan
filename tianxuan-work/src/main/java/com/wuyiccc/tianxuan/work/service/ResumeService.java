package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.*;
import com.wuyiccc.tianxuan.pojo.bo.*;
import com.wuyiccc.tianxuan.pojo.vo.ResumeVO;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/18 20:33
 */
public interface ResumeService {

    void initResume(String userId);

    void modify(EditResumeBO editResumeBO);

    ResumeVO queryMyResume(String userId);

    void editWorkExp(EditWorkExpBO editWorkExpBO);

    ResumeWorkExp getWorkExp(String workExpId, String userId);

    void deleteWorkExp(String workExpId, String userId);

    void editProjectExp(EditProjectExpBO editProjectExpBO);

    ResumeProjectExp getProjectExp(String projectExpId, String userId);

    void deleteProjectExp(String projectExpId, String userId);

    void editEducation(EditEducationBO editEducationBO);

    ResumeEducation getEducation(String eduId, String userId);

    void deleteEducation(String eduId, String userId);

    void editJobExpect(EditResumeExpectBO editResumeExpectBO);

    List<ResumeExpect> getMyResumeExpectList(String resumeId, String userId);

    void deleteMyResumeExpect(String resumeExpectId, String userId);

    void refreshResume(String resumeId, String userId);

    PagedGridResult searchResumes(SearchResumeBO searchResumeBO, Integer page, Integer limit);

    void transformAndFlushToEs(String userId);

    PagedGridResult pagedReadResumeRecordList(String hrId, Integer page, Integer pageSize);

    void hrLookCand(ResumeLookBO resumeLookBO);

    Long getWhoLookMeCount(String candUserId);

    PagedGridResult pagedWhoLookMe(String candUserId, Integer page, Integer pageSize);

    void followHr(FollowHr followHr);

    void unfollowHr(String hrId, String candUserId);

    Boolean doesCandFollowHr(String hrId, String candUserId);
}
