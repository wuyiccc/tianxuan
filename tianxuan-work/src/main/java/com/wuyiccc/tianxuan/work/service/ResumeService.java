package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.pojo.ResumeEducation;
import com.wuyiccc.tianxuan.pojo.ResumeExpect;
import com.wuyiccc.tianxuan.pojo.ResumeProjectExp;
import com.wuyiccc.tianxuan.pojo.ResumeWorkExp;
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
}
