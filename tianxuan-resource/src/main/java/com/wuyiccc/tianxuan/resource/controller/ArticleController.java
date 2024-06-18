package com.wuyiccc.tianxuan.resource.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.enumeration.ArticleStatusEnum;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.Article;
import com.wuyiccc.tianxuan.pojo.bo.NewArticleBO;
import com.wuyiccc.tianxuan.resource.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author wuyiccc
 * @date 2024/6/17 21:26
 */
@RequestMapping("adminArticle")
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping("/save")
    public R<String> save(@RequestBody @Valid NewArticleBO newArticleBO) {

        if (CharSequenceUtil.isNotBlank(newArticleBO.getId())) {
            articleService.update(newArticleBO);
        } else {
            articleService.save(newArticleBO);
        }

        return R.ok();
    }

    @PostMapping("/list")
    public R<PagedGridResult> list(@RequestParam Integer page, @RequestParam Integer limit) {

        PagedGridResult pagedGridResult = articleService.list(page, limit);
        return R.ok(pagedGridResult);
    }

    @PostMapping("/detail")
    public R<Article> detail(@RequestParam String articleId) {


        Article article = articleService.getArticleById(articleId);

        return R.ok(article);
    }

    @PostMapping("/delete")
    public R<String> delete(@RequestParam String articleId) {

        articleService.deleteArticle(articleId);
        return R.ok();
    }

    @PostMapping("/open")
    public R<String> open(@RequestParam String articleId) {

        articleService.updateStatus(articleId, ArticleStatusEnum.OPEN);
        return R.ok();
    }

    @PostMapping("/close")
    public R<String> close(@RequestParam String articleId) {

        articleService.updateStatus(articleId, ArticleStatusEnum.CLOSE);
        return R.ok();
    }

}
