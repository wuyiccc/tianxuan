package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author wuyiccc
 * @date 2024/1/6 20:42
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCompanyBO {

    @NotBlank
    private String hrUserId;

    private String realname;

    private String hrMobile;

    @NotBlank
    private String companyId;

    private String authLetter;


    /**
     * 下面是审核的时候需要用到的信息
     */
    private Integer reviewStatus;

    private String reviewReplay;
}
