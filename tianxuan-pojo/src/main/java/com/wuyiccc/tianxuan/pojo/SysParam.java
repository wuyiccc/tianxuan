package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 系统参数配置表，本表仅有一条记录
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class SysParam {


    private Integer id;

    /**
     * 每日最大简历被刷新的次数
     */
    private Integer maxResumeRefreshCounts;

}
