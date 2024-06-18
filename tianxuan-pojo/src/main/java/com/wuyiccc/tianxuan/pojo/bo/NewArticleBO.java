package com.wuyiccc.tianxuan.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/17 21:31
 */
@Data
public class NewArticleBO {

    private String id;


    private String title;

    private String content;

    private String articleCover;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = LocalDateUtils.DATETIME_PATTERN)
    private LocalDateTime publishTime;
}
