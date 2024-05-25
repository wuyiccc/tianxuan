package com.wuyiccc.tianxuan.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2024/5/25 23:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysParamVO {


    private Integer id;

    private Integer maxResumeRefreshCounts;

    private Integer version;
}
