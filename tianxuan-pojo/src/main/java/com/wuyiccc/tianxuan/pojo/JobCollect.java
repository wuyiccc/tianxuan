package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/22 15:25
 */
@Data
@NoArgsConstructor
@ToString
public class JobCollect {

    private String id;

    private String candUserId;

    private String jobId;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;
}
