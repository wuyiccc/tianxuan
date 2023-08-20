package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2023/8/20 11:20
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginBO {

    private String username;

    private String password;
}
