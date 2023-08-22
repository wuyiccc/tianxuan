package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author wuyiccc
 * @date 2023/8/22 21:35
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminBO {

    @NotBlank(message = "username不能为空")
    private String username;

    @NotBlank(message = "password不能为空")
    private String password;
}
