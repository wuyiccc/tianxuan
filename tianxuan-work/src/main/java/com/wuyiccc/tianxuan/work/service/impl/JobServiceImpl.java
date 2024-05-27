package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.common.enumeration.JobStatusEnum;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.bo.EditJobBO;
import com.wuyiccc.tianxuan.work.mapper.JobMapper;
import com.wuyiccc.tianxuan.work.service.JobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/5/27 22:41
 */
@Service
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyJobDetail(EditJobBO editJobBO) {

        Job job = new Job();
        BeanUtil.copyProperties(editJobBO, job);

        job.setUpdatedTime(LocalDateTime.now());

        if (CharSequenceUtil.isBlank(editJobBO.getId())) {
            // 新增
            job.setStatus(JobStatusEnum.OPEN.code);
            job.setCreateTime(LocalDateTime.now());
            jobMapper.insert(job);
        } else {

            LambdaQueryWrapper<Job> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Job::getId, editJobBO.getId());
            wrapper.eq(Job::getHrId, editJobBO.getHrId());
            wrapper.eq(Job::getCompanyId, editJobBO.getCompanyId());

            int res = jobMapper.update(job, wrapper);
            if (res != 1) {
                throw new CustomException("数据不存在");
            }
        }
    }

    @Override
    public PagedGridResult queryJobList(String hrId, String companyId, Integer page, Integer pageSize, Integer status) {
        PageHelper.startPage(page, pageSize);

        LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(hrId)) {
            queryWrapper.eq(Job::getHrId, hrId);
        }

        queryWrapper.eq(Job::getCompanyId, companyId);

        if (status != null) {
            if (status.equals(JobStatusEnum.OPEN.code) ||
                    status.equals(JobStatusEnum.CLOSE.code) ||
                    status.equals(JobStatusEnum.DELETE.code)) {
                queryWrapper.eq(Job::getStatus, status);
            }
        }

        queryWrapper.orderByDesc(Job::getUpdatedTime);

        List<Job> jobList = jobMapper.selectList(queryWrapper);
        return PagedGridResult.build(jobList, page);
    }
}
