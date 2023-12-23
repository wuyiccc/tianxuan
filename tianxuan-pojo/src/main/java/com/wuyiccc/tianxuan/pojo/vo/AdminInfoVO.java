package com.wuyiccc.tianxuan.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2023/12/23 22:36
 */
@Data
public class AdminInfoVO {

    private String id;

    private String username;

    private String remark;

    private String face;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedTime;
}
