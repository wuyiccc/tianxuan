package com.wuyiccc.tianxuan.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.api.interceptor.JWTCurrentUserInterceptor;
import com.wuyiccc.tianxuan.common.enumeration.ArticleStatusEnum;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.Article;
import com.wuyiccc.tianxuan.pojo.bo.NewArticleBO;
import com.wuyiccc.tianxuan.resource.mapper.ArticleMapper;
import com.wuyiccc.tianxuan.resource.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(NewArticleBO newArticleBO) {

        Article article = BeanUtil.copyProperties(newArticleBO, Article.class);
        article.setId(newArticleBO.getId());

        Admin admin = JWTCurrentUserInterceptor.adminUser.get();
        article.setPublishAdminId(admin.getId());
        article.setPublisher(admin.getUsername());
        article.setPublisherFace(admin.getFace());
        if (Objects.isNull(newArticleBO.getPublishTime())) {
            article.setStatus(ArticleStatusEnum.OPEN.type);
        } else {
            article.setStatus(ArticleStatusEnum.CLOSE.type);
        }

        LambdaQueryWrapper<Article> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Article::getStatus, ArticleStatusEnum.CLOSE.type);
        wrapper.eq(Article::getId, article.getId());

        int res = articleMapper.update(article, wrapper);
        if (res != 1) {

            throw new CustomException("文字不存在/状态不符合要求, 无法编辑");
        }

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
        LambdaQueryWrapper<Article> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(Article::getCreateTime);
        List<Article> res = articleMapper.selectList(wrapper);


        return PagedGridResult.build(res, page);
    }

    @Override
    public Article getArticleById(String articleId) {

        return articleMapper.selectById(articleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticle(String articleId) {

        articleMapper.deleteById(articleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(String articleId, ArticleStatusEnum articleStatusEnum) {


        Article newArticle = new Article();
        newArticle.setStatus(articleStatusEnum.type);
        newArticle.setId(articleId);

        articleMapper.updateById(newArticle);
    }
}
