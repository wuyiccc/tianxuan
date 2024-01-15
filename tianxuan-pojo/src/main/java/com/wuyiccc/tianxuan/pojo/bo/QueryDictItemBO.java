package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2024/1/15 20:18
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryDictItemBO {

    private String[] advantage;

    private String[] benefits;

    private String[] bonus;

    private String[] subsidy;
}
