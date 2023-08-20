package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 求职期望表
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class ResumeExpect {


    private String id;

    /**
     * 谁的求职期望，用户id
     */
    private String userId;

    /**
     * 属于哪份简历id
     */
    private String resumeId;

    /**
     * 期望职位
     */
    private String jobName;

    /**
     * 工作所在城市
     */
    private String city;

    /**
     * 工作对应所处行业
     */
    private String industry;

    /**
     * 薪资要求区间-起始
     */
    private Integer beginSalary;

    /**
     * 薪资要求区间-结束
     */
    private Integer endSalary;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;

}
