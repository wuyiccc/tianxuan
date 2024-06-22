package com.wuyiccc.tianxuan.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/22 11:31
 */
@Data
public class FollowHr {

    private String id;

    private String candUserId;

    private String hrId;

    private String hrFace;

    private String hrNickname;

    private String hrCompanyName;

    private String hrPosition;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;
}
