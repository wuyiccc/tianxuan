package com.wuyiccc.tianxuan.resource.controller;

import cn.hutool.core.text.StrPool;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.enumeration.ArticleStatusEnum;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.pojo.Article;
import com.wuyiccc.tianxuan.resource.service.ArticleService;
import com.wuyiccc.tianxuan.resource.service.ResumeCollectService;
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

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ResumeCollectService resumeCollectService;

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

    @PostMapping("/read")
    public R<String> read(@RequestParam String userId, @RequestParam String articleId) {

        redisUtils.hyperLogAdd(BaseInfoProperties.REDIS_ARTICLE_READ_COUNTS + StrPool.COLON + articleId, userId);
        return R.ok();
    }

    @PostMapping("/addCollect")
    public R<String> addCollect(@RequestParam String hrId, @RequestParam String resumeExpectId) {

        // 新增简历收藏
        resumeCollectService.addCollect(hrId, resumeExpectId);

        return R.ok();
    }


    @PostMapping("/removeCollect")
    public R<String> removeCollect(@RequestParam String hrId, @RequestParam String resumeExpectId) {

        resumeCollectService.removeCollect(hrId, resumeExpectId);
        return R.ok();
    }

    @PostMapping("/isHrCollectResume")
    public R<Boolean> isHrCollectResume(@RequestParam String hrId, @RequestParam String resumeExpectId) {

        Boolean flag = resumeCollectService.isHrCollectResume(hrId, resumeExpectId);
        return R.ok(flag);
    }


}
