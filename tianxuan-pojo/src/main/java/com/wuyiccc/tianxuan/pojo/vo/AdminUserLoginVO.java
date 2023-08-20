package com.wuyiccc.tianxuan.pojo.vo;

import com.wuyiccc.tianxuan.pojo.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2023/8/20 11:35
 */
@Data
@ToString
@NoArgsConstructor
public class AdminUserLoginVO {

    private String id;

    private String token;

    private Admin admin;
}
