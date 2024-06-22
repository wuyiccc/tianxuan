package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.api.remote.CompanyRemoteApi;
import com.wuyiccc.tianxuan.api.remote.UserInfoRemoteApi;
import com.wuyiccc.tianxuan.common.enumeration.JobStatusEnum;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.bo.EditJobBO;
import com.wuyiccc.tianxuan.pojo.bo.SearchJobsBO;
import com.wuyiccc.tianxuan.pojo.vo.CompanyInfoVO;
import com.wuyiccc.tianxuan.pojo.vo.SearchJobsVO;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
import com.wuyiccc.tianxuan.work.mapper.JobMapper;
import com.wuyiccc.tianxuan.work.service.JobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wuyiccc
 * @date 2024/5/27 22:41
 */
@Service
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;

    @Resource
    private UserInfoRemoteApi userInfoRemoteApi;

    @Resource
    private CompanyRemoteApi companyRemoteApi;

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

    @Override
    public Job queryJobDetail(String hrId, String companyId, String jobId) {

        List<Integer> statusList = new ArrayList<>();
        statusList.add(JobStatusEnum.OPEN.code);
        statusList.add(JobStatusEnum.CLOSE.code);
        statusList.add(JobStatusEnum.DELETE.code);

        LambdaQueryWrapper<Job> wrapper = Wrappers.lambdaQuery();
        if (CharSequenceUtil.isNotBlank(jobId)) {
            wrapper.eq(Job::getId, jobId);
        }
        if (CharSequenceUtil.isNotBlank(hrId)) {
            wrapper.eq(Job::getHrId, hrId);
        }
        if (CharSequenceUtil.isNotBlank(companyId)) {
            wrapper.eq(Job::getCompanyId, companyId);
        }

        wrapper.in(Job::getStatus, statusList);

        List<Job> jobList = jobMapper.selectList(wrapper);

        if (CollUtil.isEmpty(jobList)) {
            return null;
        }

        return jobList.get(0);
    }

    @Override
    public void modifyStatus(String hrId, String companyId, String jobId, Integer jobStatus) {

        Job job = new Job();
        job.setStatus(jobStatus);
        job.setUpdatedTime(LocalDateTime.now());

        LambdaQueryWrapper<Job> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Job::getId, jobId);
        wrapper.eq(Job::getHrId, hrId);
        wrapper.eq(Job::getCompanyId, companyId);
        int res = jobMapper.update(job, wrapper);
        if (res != 1) {
            throw new CustomException("更新职位状态失败");
        }
    }

    @Override
    public PagedGridResult searchJobs(SearchJobsBO searchJobsBO, Integer page, Integer limit) {

        String jobName = searchJobsBO.getJobName();
        String jobType = searchJobsBO.getJobType();
        String city = searchJobsBO.getCity();
        Integer beginSalary = searchJobsBO.getBeginSalary();
        Integer endSalary = searchJobsBO.getEndSalary();


        PageHelper.startPage(page, limit);

        LambdaQueryWrapper<Job> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Job::getStatus, JobStatusEnum.OPEN.code);

        if (CharSequenceUtil.isNotBlank(jobName)) {
            wrapper.like(Job::getJobName, jobName);
        }

        if (CharSequenceUtil.isNotBlank(jobType)) {
            wrapper.like(Job::getJobType, jobType);
        }
        if (CharSequenceUtil.isNotBlank(city)) {
            wrapper.like(Job::getCity, city);
        }


        if (beginSalary > 0 && endSalary > 0) {

            // 符合求职薪资范围的职位薪资有三种情况
            wrapper.and(qw -> qw.or(
                                    subQW -> subQW.ge(Job::getEndSalary, beginSalary)
                                            .le(Job::getBeginSalary, endSalary)
                            )
                            .or(subQW -> subQW.ge(Job::getEndSalary, endSalary)
                                    .le(Job::getBeginSalary, beginSalary))
                            .or(subQW -> subQW.ge(Job::getBeginSalary, beginSalary)
                                    .le(Job::getEndSalary, endSalary)
                            )
            );
        }


        List<Job> jobList = jobMapper.selectList(wrapper);

        if (CollUtil.isEmpty(jobList)) {
            PagedGridResult pagedGridResult = new PagedGridResult();
            pagedGridResult.setPage(page);
            pagedGridResult.setTotal(0);
            pagedGridResult.setRecords(0);
            pagedGridResult.setRows(ListUtil.empty());
            return pagedGridResult;
        }


        List<String> hrIdList = jobList.stream().map(Job::getHrId).collect(Collectors.toList());
        List<String> companyIdList = jobList.stream().map(Job::getCompanyId).collect(Collectors.toList());

        R<List<UserVO>> userVOListRes = userInfoRemoteApi.getList(hrIdList);
        List<UserVO> userVOList = userVOListRes.getData();
        Map<String, UserVO> userCache = CollStreamUtil.toIdentityMap(userVOList, UserVO::getId);

        R<List<CompanyInfoVO>> companyInfoVORes = companyRemoteApi.getList(companyIdList);
        List<CompanyInfoVO> companyInfoVOList = companyInfoVORes.getData();
        Map<String, CompanyInfoVO> companyCache = CollStreamUtil.toIdentityMap(companyInfoVOList, CompanyInfoVO::getCompanyId);


        PagedGridResult res = PagedGridResult.build(jobList, page);

        List<SearchJobsVO> voList = new ArrayList<>();

        for (Job job : jobList) {

            SearchJobsVO vo = new SearchJobsVO();
            BeanUtil.copyProperties(job, vo);

            UserVO userVO = userCache.get(job.getHrId());
            vo.setUsersVO(userVO);

            CompanyInfoVO companyInfoVO = companyCache.get(job.getCompanyId());
            vo.setCompanyInfoVO(companyInfoVO);

            voList.add(vo);
        }
        res.setRows(voList);
        return res;
    }

    @Override
    public Long countJob(String hrId) {

        LambdaQueryWrapper<Job> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Job::getHrId, hrId);

        return jobMapper.selectCount(wrapper);
    }

}
