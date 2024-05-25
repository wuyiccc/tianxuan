package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.JobType;
import com.wuyiccc.tianxuan.pojo.vo.JobTypeSecondAndThirdVO;
import com.wuyiccc.tianxuan.pojo.vo.JobTypeThirdVO;
import com.wuyiccc.tianxuan.work.mapper.JobTypeMapper;
import com.wuyiccc.tianxuan.work.service.JobTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wuyiccc
 * @date 2024/5/25 09:46
 */
@Slf4j
@Service
public class JobTypeServiceImpl implements JobTypeService {

    @Resource
    private JobTypeMapper jobTypeMapper;

    @Override
    public List<JobType> initTopList() {

        LambdaQueryWrapper<JobType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(JobType::getFatherId, 0);
        wrapper.orderByAsc(JobType::getSort);

        return jobTypeMapper.selectList(wrapper);
    }

    @Override
    public List<JobType> getThirdListByTop(String topJobTypeId) {

        LambdaQueryWrapper<JobType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(JobType::getFatherId, topJobTypeId);

        List<JobType> secondList = jobTypeMapper.selectList(wrapper);


        List<String> secondIdList = secondList.stream().map(JobType::getId).collect(Collectors.toList());

        if (CollUtil.isEmpty(secondIdList)) {

            return ListUtil.empty();
        }

        LambdaQueryWrapper<JobType> thirdWrapper = Wrappers.lambdaQuery();
        thirdWrapper.in(JobType::getFatherId, secondIdList);

        return jobTypeMapper.selectList(thirdWrapper);
    }

    @Override
    public List<JobTypeSecondAndThirdVO> getSecondAndThirdListByTop(String topJobTypeId) {

        // 拿到二级信息
        LambdaQueryWrapper<JobType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(JobType::getFatherId, topJobTypeId);

        List<JobType> jobTypeList = jobTypeMapper.selectList(wrapper);

        if (CollUtil.isEmpty(jobTypeList)) {
            return ListUtil.empty();
        }

        // 拿到对应的三级信息
        List<String> secondIdList = jobTypeList.stream().map(JobType::getId).collect(Collectors.toList());

        LambdaQueryWrapper<JobType> thirdWrapper = Wrappers.lambdaQuery();
        thirdWrapper.in(JobType::getFatherId, secondIdList);

        List<JobType> thirdJobTypeList = jobTypeMapper.selectList(thirdWrapper);

        // fatherId分组
        Map<String, List<JobType>> thirdGroup = CollStreamUtil.groupByKey(thirdJobTypeList, JobType::getFatherId);


        List<JobTypeSecondAndThirdVO> resList = new ArrayList<>();

        for (JobType jobType : jobTypeList) {

            JobTypeSecondAndThirdVO vo = new JobTypeSecondAndThirdVO();
            vo.setSecondLevelId(jobType.getId());
            vo.setSecondLevelName(jobType.getName());
            vo.setSecondLevelSort(jobType.getSort());

            List<JobType> subJobTypeList = thirdGroup.get(jobType.getId());

            List<JobTypeThirdVO> subVOList = new ArrayList<>(subJobTypeList.size());
            subJobTypeList.forEach(e -> {
                JobTypeThirdVO subVO = new JobTypeThirdVO();
                subVO.setThirdLevelId(e.getId());
                subVO.setThirdLevelName(e.getName());
                subVO.setThirdLevelSort(e.getSort());
                subVOList.add(subVO);
            });
            vo.setJobTypeThirdVOList(subVOList);
            resList.add(vo);
        }

        return resList;
    }

    @Override
    public List<JobType> getChildrenList(String jobTypeId) {
        LambdaQueryWrapper<JobType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(JobType::getFatherId, jobTypeId);
        wrapper.orderByAsc(JobType::getSort);
        return jobTypeMapper.selectList(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createNode(JobType jobType) {

        LambdaQueryWrapper<JobType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(JobType::getName, jobType.getName());

        List<JobType> jobTypeList = jobTypeMapper.selectList(wrapper);
        if (CollUtil.isNotEmpty(jobTypeList)) {
            throw new CustomException("节点已重复");
        }

        // 新建节点
        jobTypeMapper.insert(jobType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNode(JobType jobType) {

        int res = jobTypeMapper.updateById(jobType);
        if (res != 1) {
            throw new CustomException("节点不存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteNode(String jobTypeId) {

        JobType jobType = jobTypeMapper.selectById(jobTypeId);
        if (Objects.isNull(jobType)) {
            throw new CustomException("科目类型不存在");
        }

        // 检查子节点
        LambdaQueryWrapper<JobType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(JobType::getFatherId, jobType.getId());


        Long count = jobTypeMapper.selectCount(wrapper);
        if (count > 0) {
            throw new CustomException("该节点下存在子节点无法删除");
        }


        int res = jobTypeMapper.deleteById(jobTypeId);
        if (res != 1) {
            throw new CustomException("删除失败");
        }
    }
}
