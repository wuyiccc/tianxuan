package com.wuyiccc.tianxuan.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/2 10:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchReportJobBO {

    private String jobName;

    private String companyName;

    private String reportUserName;

    private Integer dealStatus;


    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private LocalDateTime beginDateTime;

    private LocalDateTime endDateTime;
}
