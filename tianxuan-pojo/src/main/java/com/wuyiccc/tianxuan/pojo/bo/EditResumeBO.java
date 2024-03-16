package com.wuyiccc.tianxuan.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/3/16 14:44
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditResumeBO {

    private String id;

    private String userId;

    private String advantage;

    private String advantageHtml;

    private String credentials;

    private String skills;

    private String status;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public LocalDateTime refreshTime;
}
