package com.wuyiccc.tianxuan.work.service;

import com.wuyiccc.tianxuan.pojo.bo.EditResumeBO;
import com.wuyiccc.tianxuan.pojo.bo.EditWorkExpBO;
import com.wuyiccc.tianxuan.pojo.vo.ResumeVO;

/**
 * @author wuyiccc
 * @date 2023/12/18 20:33
 */
public interface ResumeService {

    void initResume(String userId);

    void modify(EditResumeBO editResumeBO);

    ResumeVO queryMyResume(String userId);

    void editWorkExp(EditWorkExpBO editWorkExpBO);
}
