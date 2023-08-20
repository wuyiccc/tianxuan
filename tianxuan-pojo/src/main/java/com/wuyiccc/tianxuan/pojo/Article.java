package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class Article {


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

}
