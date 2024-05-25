package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2024/5/25 17:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditResumeExpectBO {

    private String id;

    private String userId;

    private String resumeId;

    private String jobName;

    private String city;

    private String industry;

    private Integer beginSalary;

    private Integer endSalary;
}
