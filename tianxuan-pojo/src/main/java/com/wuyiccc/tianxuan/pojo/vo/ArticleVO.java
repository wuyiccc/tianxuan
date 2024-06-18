package com.wuyiccc.tianxuan.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/19 00:12
 */
@Data
public class ArticleVO {


    private String id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容，长度不超过9999，自行在前后端判断
     */
    private String content;

    /**
     * 文章封面图，article_type=1 的时候展示
     */
    private String articleCover;

    /**
     * 发布者amin_id
     */
    private String publishAdminId;

    /**
     * 文章发布时间（也是预约发布的时间）
     */
    private LocalDateTime publishTime;

    /**
     * 发布者，字段冗余，避免多表关联
     */
    private String publisher;

    /**
     * 发布者头像，字段冗余
     */
    private String publisherFace;

    /**
     * 文章状态：0：关闭，待发布，1：正常，可查阅，2：删除，无法查看
     */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    private Long readCounts;
}
