package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 面试邀约表
本表为次表，可做冗余，可以用mongo或者es替代
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class Interview {


    private String id;

    /**
     * 本面试属于哪个hr的
     */
    private String hrUserId;

    /**
     * 本面试属于哪一个公司的
     */
    private String companyId;

    /**
     * 面试者，候选人id
     */
    private String candUserId;

    /**
     * 面试的岗位id
     */
    private String jobId;

    /**
     * 面试的岗位名称
     */
    private String jobName;

    /**
     * 面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 面试地点
     */
    private String interviewAddress;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 面试邀约的状态
1：等待候选人接受面试
2：候选人已接受面试
3：候选人已拒绝面试
4：HR已取消面试
5：面试通过
     */
    private Integer status;

    /**
     * 候选人名称（候选人名称）  
简历名称与职位使用字段冗余，目的相当于快照，只记录当时信息
     */
    private String candName;

    /**
     * 候选人头像  
简历名称与职位使用字段冗余，目的相当于快照，只记录当时信息
     */
    private String candFace;

    /**
     * 候选人职位  
简历名称与职位使用字段冗余，目的相当于快照，只记录当时信息
     */
    private String candPosition;
}
