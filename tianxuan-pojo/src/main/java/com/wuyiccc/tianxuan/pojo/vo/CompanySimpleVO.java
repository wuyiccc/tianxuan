package com.wuyiccc.tianxuan.pojo.vo;

import lombok.Data;

/**
 * @author wuyiccc
 * @date 2023/12/24 16:03
 */
@Data
public class CompanySimpleVO {

    private String id;

    private String companyName;

    private String shortName;

    private String logo;

    private String peopleSize;

    private String industry;

    private String nature;

    private String address;

    /**
     * 审核状态
     * 0 未发起审核认证(未进入审核流程)
     * 1 审核认证通过
     * 2 审核认证失败
     * 3 审核中 (等待审核)
     */
    private Integer reviewStatus;

    private String reviewReplay;

    // 企业下所绑定的HR数量
    private Long hrCounts = Long.valueOf("0");
}
