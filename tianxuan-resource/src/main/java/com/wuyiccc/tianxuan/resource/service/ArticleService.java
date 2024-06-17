package com.wuyiccc.tianxuan.resource.service;

import com.wuyiccc.tianxuan.pojo.Article;
import com.wuyiccc.tianxuan.pojo.bo.NewArticleBO;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/17 21:29
 */
public interface ArticleService {
    void save(NewArticleBO newArticleBO);


    List<Article> listWaitPublish();

    void publishArticle(String id);
}
