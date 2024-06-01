package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/1 00:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchResumeBO {

    private String companyId;

    private String basicTitle;
    private String jobType;

    private Integer beginAge;


    private Integer endAge;

    private Integer sex;

    // 活跃度
    private String activeTime;

    private Integer activeTimes;    // 后端根据前端传来的[activeTime字符串]从枚举中计算获得的[活跃度时间(秒)]

    // 工作经验
    private String workExpYears;
    private Integer beginWorkExpYears;
    private Integer endWorkExpYears;

    // 学历要求
    private String edu;
    private List<String> eduList;

    // 薪资待遇
    private Integer beginSalary;
    private Integer endSalary;

    // 求职状态
    private String jobStatus;
}
