package com.wuyiccc.tianxuan.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/22 10:18
 */
@Data
public class ResumeLook {

    private String id;

    private String hrId;

    private String hrCompanyId;

    private String hrFace;

    private String hrNickname;

    private String hrCompanyName;

    private String hrPosition;

    private String candUserId;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;
}
