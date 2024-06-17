package com.wuyiccc.tianxuan.resource.controller;

import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.bo.NewArticleBO;
import com.wuyiccc.tianxuan.resource.service.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
