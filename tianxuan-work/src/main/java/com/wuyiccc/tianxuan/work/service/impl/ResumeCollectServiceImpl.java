package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuyiccc.tianxuan.api.remote.ResumeSearchRemoteApi;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.ResumeCollect;
import com.wuyiccc.tianxuan.pojo.ResumeRead;
import com.wuyiccc.tianxuan.pojo.vo.ResumeEsVO;
import com.wuyiccc.tianxuan.work.mapper.ResumeCollectMapper;
import com.wuyiccc.tianxuan.work.mapper.ResumeReadMapper;
import com.wuyiccc.tianxuan.work.service.ResumeCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyiccc
 * @date 2024/6/19 20:50
 */
@Slf4j
@Service
public class ResumeCollectServiceImpl implements ResumeCollectService {

    @Resource
    private ResumeCollectMapper resumeCollectMapper;

    @Resource
    private ResumeSearchRemoteApi resumeSearchRemoteApi;

    @Resource
    private ResumeReadMapper resumeReadMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCollect(String hrId, String resumeExpectId) {


        ResumeCollect resumeCollect = new ResumeCollect();
        resumeCollect.setUserId(hrId);
        resumeCollect.setResumeExpectId(resumeExpectId);

        resumeCollect.setCreateTime(LocalDateTime.now());
        resumeCollect.setUpdatedTime(LocalDateTime.now());

        resumeCollectMapper.insert(resumeCollect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeCollect(String hrId, String resumeExpectId) {

        LambdaQueryWrapper<ResumeCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeCollect::getUserId, hrId);
        wrapper.eq(ResumeCollect::getResumeExpectId, resumeExpectId);

        int res = resumeCollectMapper.delete(wrapper);
        if (res <= 0) {
            throw new CustomException("数据不存在");
        }
    }


    @Override
    public Boolean isHrCollectResume(String hrId, String resumeExpectId) {

        LambdaQueryWrapper<ResumeCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeCollect::getUserId, hrId);
        wrapper.eq(ResumeCollect::getResumeExpectId, resumeExpectId);

        List<ResumeCollect> resumeCollectList = resumeCollectMapper.selectList(wrapper);
        return !CollUtil.isEmpty(resumeCollectList);
    }

    @Override
    public Long getCollectResumeCount(String hrUserId) {

        LambdaQueryWrapper<ResumeCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeCollect::getUserId, hrUserId);
        return resumeCollectMapper.selectCount(wrapper);
    }

    @Override
    public PagedGridResult pagedCollectResumeList(String hrId, Integer page, Integer pageSize) {

        LambdaQueryWrapper<ResumeCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeCollect::getUserId, hrId);

        PageHelper.startPage(page + 1, pageSize);
        List<ResumeCollect> rList = resumeCollectMapper.selectList(wrapper);
        PageInfo<ResumeCollect> midPage = new PageInfo<>(rList);

        // 拿到id
        List<String> resumeExpectedIdList = midPage.getList().stream().map(ResumeCollect::getResumeExpectId).collect(Collectors.toList());


        R<List<ResumeEsVO>> listR = resumeSearchRemoteApi.searchByIds(resumeExpectedIdList);

        return PagedGridResult.build(listR.getData(), page);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveReadResumeRecord(String hrId, String resumeExpectId) {


        ResumeRead resumeRead = new ResumeRead();
        resumeRead.setUserId(hrId);
        resumeRead.setResumeExpectId(resumeExpectId);
        resumeRead.setCreateTime(LocalDateTime.now());
        resumeRead.setUpdatedTime(LocalDateTime.now());

        resumeReadMapper.insert(resumeRead);
    }

    @Override
    public Long getReadResumeRecordCounts(String hrId) {
        LambdaQueryWrapper<ResumeRead> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeRead::getUserId, hrId);
        return resumeReadMapper.selectCount(wrapper);
    }
}
