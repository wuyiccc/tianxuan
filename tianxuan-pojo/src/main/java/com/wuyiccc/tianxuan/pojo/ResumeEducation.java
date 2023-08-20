package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 教育经历表
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class ResumeEducation {


    private String id;

    /**
     * 谁的教育经历，用户id
     */
    private String userId;

    /**
     * 属于哪份简历id
     */
    private String resumeId;

    /**
     * 学校名称
     */
    private String school;

    /**
     * 学历
     */
    private String education;

    /**
     * 专业名称
     */
    private String major;

    /**
     * 开始日期
     */
    private String beginDate;

    /**
     * 结束日期
     */
    private String endDate;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;


}
