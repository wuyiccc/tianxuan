package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2023/12/10 22:46
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResetPwdBO {

    private String adminId;

    private String password;

    private String rePassword;
}
