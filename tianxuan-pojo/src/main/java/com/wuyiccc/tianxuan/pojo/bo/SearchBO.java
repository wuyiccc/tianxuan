package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/3/3 20:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchBO {

    public List<String> userIds;

    public List<String> companyIds;
}
