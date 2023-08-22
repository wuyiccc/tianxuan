package com.wuyiccc.tianxuan.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 运营管理系统的admin账户表，仅登录，不提供注册
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class Admin{

    private String id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 用户混合加密的盐
     */
    @JsonIgnore
    private String slat;

    /**
     * 备注
     */
    private String remark;

    /**
     * 头像
     */
    private String face;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;
}
