package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2023/8/30 21:05
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminBO {

    private String username;

    private String password;

    private String remark;
}
