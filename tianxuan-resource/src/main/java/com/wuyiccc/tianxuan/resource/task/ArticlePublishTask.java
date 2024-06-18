package com.wuyiccc.tianxuan.resource.task;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.common.enumeration.ArticleStatusEnum;
import com.wuyiccc.tianxuan.pojo.Article;
import com.wuyiccc.tianxuan.resource.service.ArticleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/18 00:15
 */
@Component
public class ArticlePublishTask {


    @Resource
    private ArticleService articleService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void publish() {


        List<Article> articles = articleService.listWaitPublish();

        for (Article article : articles) {
            LocalDateTime publishTime = article.getPublishTime();
            if (Objects.isNull(publishTime)) {
                articleService.publishArticle(article.getId());
                continue;
            }
            long offset = LocalDateTimeUtil.between(publishTime, LocalDateTime.now(), ChronoUnit.SECONDS);
            if (offset >= 0) {
                articleService.publishArticle(article.getId());
            }
        }

    }

}
