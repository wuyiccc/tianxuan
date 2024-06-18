package com.wuyiccc.tianxuan.resource.controller;

import com.dingtalk.api.request.OapiMessageMassSendRequest;
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

        articleService.save(newArticleBO);

        return R.ok();
    }

    @PostMapping("/list")
    public R<PagedGridResult> list(@RequestParam Integer page, @RequestParam Integer limit) {

        PagedGridResult pagedGridResult = articleService.list(page, limit);
        return R.ok(pagedGridResult);
    }

}
