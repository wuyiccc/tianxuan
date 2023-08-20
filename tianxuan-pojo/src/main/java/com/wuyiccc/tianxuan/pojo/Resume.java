package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 简历表
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class Resume {


    private String id;

    /**
     * 谁的简历，用户id
     */
    private String userId;

    /**
     * 个人优势
     */
    private String advantage;

    /**
     * 个人优势的html内容，用于展示
     */
    private String advantageHtml;

    /**
     * 资格证书
     */
    private String credentials;

    /**
     * 技能标签
     */
    private String skills;

    /**
     * 求职状态
     */
    private String status;

    /**
     * 刷新简历时间
     */
    private LocalDateTime refreshTime;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;

}
