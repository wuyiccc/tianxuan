package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/19 20:47
 * 简历收藏实体
 */

@Data
@NoArgsConstructor
@ToString
public class ResumeCollect {

    private String id;

    private String userId;

    private String resumeExpectId;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;
}
