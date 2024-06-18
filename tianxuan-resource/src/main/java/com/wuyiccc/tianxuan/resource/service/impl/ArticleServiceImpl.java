package com.wuyiccc.tianxuan.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.enumeration.ArticleStatusEnum;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.Article;
import com.wuyiccc.tianxuan.pojo.bo.NewArticleBO;
import com.wuyiccc.tianxuan.resource.mapper.ArticleMapper;
import com.wuyiccc.tianxuan.resource.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/17 21:30
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    public ArticleMapper articleMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(NewArticleBO newArticleBO) {

        Article article = new Article();
        BeanUtil.copyProperties(newArticleBO, article);


        Admin admin = JWTCurrentUserInterceptor.adminUser.get();
        String publisher = admin.getId();
        article.setPublishAdminId(publisher);
        article.setPublishTime(newArticleBO.getPublishTime());
        article.setPublisher(admin.getUsername());
        String face = admin.getFace();
        article.setPublisherFace(face);


        if (Objects.isNull(newArticleBO.getPublishTime())) {
            article.setStatus(ArticleStatusEnum.OPEN.type);
        } else {
            article.setStatus(ArticleStatusEnum.CLOSE.type);
        }

        article.setCreateTime(LocalDateTime.now());

        article.setUpdateTime(LocalDateTime.now());

        articleMapper.insert(article);
    }

    @Override
    public List<Article> listWaitPublish() {
        LambdaQueryWrapper<Article> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Article::getStatus, ArticleStatusEnum.CLOSE.type);

        return articleMapper.selectList(wrapper);
    }

    @Override
    public void publishArticle(String id) {

        LambdaQueryWrapper<Article> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Article::getId, id);
        wrapper.eq(Article::getStatus, ArticleStatusEnum.CLOSE.type);

        Article article = new Article();
        article.setStatus(ArticleStatusEnum.OPEN.type);
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article, wrapper);
    }

    @Override
    public PagedGridResult list(Integer page, Integer limit) {

        PageHelper.startPage(page, limit);
        List<Article> res = articleMapper.selectList(Wrappers.lambdaQuery());

        return PagedGridResult.build(res, page);
    }
}
