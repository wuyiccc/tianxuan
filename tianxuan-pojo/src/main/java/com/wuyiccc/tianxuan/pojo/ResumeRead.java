package com.wuyiccc.tianxuan.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/21 22:22
 */
@Data
public class ResumeRead {

    private String id;

    private String userId;

    private String resumeExpectId;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;
}
