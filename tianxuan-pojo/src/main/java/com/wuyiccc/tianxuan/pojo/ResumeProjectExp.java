package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 项目经验表
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class ResumeProjectExp {


    private String id;

    /**
     * 谁的项目经验，用户id
     */
    private String userId;

    /**
     * 属于哪份简历id
     */
    private String resumeId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 担任角色
     */
    private String roleName;

    /**
     * 项目开始日期
     */
    private String beginDate;

    /**
     * 项目结束日期
     */
    private String endDate;

    /**
     * 项目描述
     */
    private String content;

    /**
     * 项目描述
     */
    private String contentHtml;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;
}
