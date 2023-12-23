package com.wuyiccc.tianxuan.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wuyiccc
 * @date 2023/12/23 23:12
 */
@Data
public class Base64FileBO {


    @NotBlank
    private String base64File;
}
