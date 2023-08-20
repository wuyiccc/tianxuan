package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 举报职位
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class ReportJob {


    private String id;

    /**
     * 被举报的职位id
     */
    private String jobId;

    /**
     * 举报人id
     */
    private String reportUserId;

    /**
     * 举报人姓名
     */
    private String reportUserName;

    /**
     * 举报原因
     */
    private String reportReason;

    /**
     * 被举报的职位名称
     */
    private String jobName;

    /**
     * 被举报的公司名称
     */
    private String companyName;

    /**
     * 处理状态：0：待处理，1：已处理，2：忽略、无需处理
     */
    private Integer dealStatus;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

}
