package com.wuyiccc.tianxuan.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/1 06:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResumeDTO {

    /**
     * 模糊查询字段
     */
    private String basicTitle;

    private String jobType;

    private Integer beginAge;

    private Integer endAge;

    private Integer sex;

    private Integer activeTimes;

    private Integer beginWorkExpYears;

    private Integer endWorkExpYears;

    private String edu;

    private List<String> eduList;

    private Integer beginSalary;

    private Integer endSalary;

    private String jobStatus;


}
