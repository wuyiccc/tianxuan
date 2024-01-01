package com.wuyiccc.tianxuan.pojo.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 企业表
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class CreateCompanyBO {

    private String companyId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业短名
     */
    private String shortName;

    /**
     * 企业logo
     */
    private String logo;

    /**
     * 营业执照
     */
    private String bizLicense;

    /**
     * 企业所在国家
     */
    private String country;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 所在市
     */
    private String city;

    /**
     * 所在区县
     */
    private String district;

    /**
     * 公司办公地址
     */
    private String address;

    /**
     * 公司性质
     */
    private String nature;

    /**
     * 人员规模/企业规模
     */
    private String peopleSize;

    /**
     * 所在行业
     */
    private String industry;

    /**
     * 融资阶段
     */
    private String financStage;

    /**
     * 工作时间，例：9:00-18:00 周末单休
     */
    private String workTime;

    /**
     * 公司简介
     */
    private String introduction;

    /**
     * 公司优势，例：领导和睦 岗位晋升
     */
    private String advantage;

    /**
     * 福利待遇，例：五险一金 定期体检
     */
    private String benefits;

    /**
     * 薪资福利（奖金），例：年底双薪
     */
    private String bonus;

    /**
     * 补助津贴，例：住房补贴
     */
    private String subsidy;

    /**
     * 成立时间
     */
    private LocalDate buildDate;

    /**
     * 注册资本
     */
    private String registCapital;

    /**
     * 注册地址
     */
    private String registPlace;

    /**
     * 法人代表
     */
    private String legalRepresentative;

    /**
     * 审核状态
     * 0：未发起审核认证(未进入审核流程)
     * 1：审核认证通过
     * 2：审核认证失败
     * 3：审核中（等待审核）
     */
    private Integer reviewStatus;

    /**
     * 审核回复/审核意见
     */
    private String reviewReplay;

    /**
     * 入驻平台授权书
     */
    private String authLetter;

    /**
     * 提交申请人的用户id
     */
    private String commitUserId;

    /**
     * 提交申请人的手机号
     */
    private String commitUserMobile;

    /**
     * 提交审核的日期
     */
    private LocalDate commitDate;

    /**
     * 0: 否  1: 是
     */
    private Integer isVip;

    /**
     * Vip过期日期，判断企业是否vip，需要is_vip=1并且过期日期>=当前日期
     */
    private LocalDate vipExpireDate;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

}
