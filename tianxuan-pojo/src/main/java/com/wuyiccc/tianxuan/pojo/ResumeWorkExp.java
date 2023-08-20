package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 工作经验表
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class ResumeWorkExp {


    private String id;

    /**
     * 谁的工作经验，用户id
     */
    private String userId;

    /**
     * 属于哪份简历id
     */
    private String resumeId;

    /**
     * 就职公司名称
     */
    private String companyName;

    /**
     * 行业
     */
    private String industry;

    /**
     * 在职时间-开始
     */
    private String beginDate;

    /**
     * 在职时间-结束
     */
    private String endDate;

    /**
     * 职位名称
     */
    private String position;

    /**
     * 所在部门名称
     */
    private String department;

    /**
     * 工作内容、业绩、职责等
     */
    private String content;

    /**
     * 工作内容、业绩、职责等
     */
    private String contentHtml;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;

}
