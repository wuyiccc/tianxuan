package com.wuyiccc.tianxuan.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/23 13:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateInterviewBO {


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
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = LocalDateUtils.DATETIME_PATTERN)
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
     * 候选人名称（候选人名称）
     * 简历名称与职位使用字段冗余，目的相当于快照，只记录当时信息
     */
    private String candName;

    /**
     * 候选人头像
     * 简历名称与职位使用字段冗余，目的相当于快照，只记录当时信息
     */
    private String candFace;

    /**
     * 候选人职位
     * 简历名称与职位使用字段冗余，目的相当于快照，只记录当时信息
     */
    private String candPosition;
}
