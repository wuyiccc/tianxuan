package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2024/5/29 22:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchJobsBO {

    private String jobName;

    private String jobType;

    private String city;

    private Integer beginSalary;

    private Integer endSalary;
}
