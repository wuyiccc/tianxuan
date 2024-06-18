package com.wuyiccc.tianxuan.resource.controller;

import com.wuyiccc.tianxuan.common.enumeration.ArticleStatusEnum;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.Article;
import com.wuyiccc.tianxuan.resource.service.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/18 22:46
 */
@RequestMapping("appArticle")
@RestController
public class AppArticleController {

    @Resource
    private ArticleService articleService;


    @PostMapping("/list")
    public R<PagedGridResult> list(@RequestParam Integer page, @RequestParam Integer limit) {

        PagedGridResult list = articleService.list(page, limit, ArticleStatusEnum.OPEN);
        return R.ok(list);
    }

    @PostMapping("/detail")
    public R<Article> detail(@RequestParam String articleId) {


        Article article = articleService.getArticleById(articleId);

        return R.ok(article);
    }
}
