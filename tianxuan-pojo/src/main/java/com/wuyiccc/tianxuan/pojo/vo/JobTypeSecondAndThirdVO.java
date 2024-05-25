package com.wuyiccc.tianxuan.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/5/25 14:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobTypeSecondAndThirdVO {

    private String secondLevelId;

    private String secondLevelName;

    private Integer secondLevelSort;

    private List<JobTypeThirdVO> jobTypeThirdVOList;
}
