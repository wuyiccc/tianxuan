package com.wuyiccc.tianxuan.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuyiccc.tianxuan.api.remote.ResumeSearchRemoteApi;
import com.wuyiccc.tianxuan.api.remote.UserInfoRemoteApi;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.*;
import com.wuyiccc.tianxuan.pojo.bo.*;
import com.wuyiccc.tianxuan.pojo.dto.ResumeEsCreateDTO;
import com.wuyiccc.tianxuan.pojo.dto.SearchResumeDTO;
import com.wuyiccc.tianxuan.pojo.vo.ResumeEsVO;
import com.wuyiccc.tianxuan.pojo.vo.ResumeVO;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
import com.wuyiccc.tianxuan.work.mapper.*;
import com.wuyiccc.tianxuan.work.service.ResumeEducationService;
import com.wuyiccc.tianxuan.work.service.ResumeProjectExpService;
import com.wuyiccc.tianxuan.work.service.ResumeService;
import com.wuyiccc.tianxuan.work.service.ResumeWorkExpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuyiccc
 * @date 2023/12/18 20:33
 */
@Slf4j
@Service
public class ResumeServiceImpl implements ResumeService {

    @Resource
    private ResumeMapper resumeMapper;


    @Resource
    private ResumeEducationService resumeEducationService;

    @Resource
    private ResumeProjectExpService resumeProjectExpService;


    @Resource
    private ResumeWorkExpService resumeWorkExpService;

    @Resource
    private ResumeProjectExpMapper resumeProjectExpMapper;

    @Resource
    private ResumeEducationMapper resumeEducationMapper;

    @Resource
    private ResumeExpectMapper resumeExpectMapper;


    @Resource
    private UserInfoRemoteApi userInfoRemoteApi;

    @Resource
    private ResumeSearchRemoteApi resumeSearchRemoteApi;

    @Resource
    private ResumeReadMapper resumeReadMapper;

    @Resource
    private ResumeLookMapper resumeLookMapper;

    @Resource
    private FollowHrMapper followHrMapper;

    @Resource
    private JobCollectMapper jobCollectMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initResume(String userId) {

        Resume resume = new Resume();
        resume.setUserId(userId);
        resume.setCreateTime(LocalDateTime.now());
        resume.setUpdatedTime(LocalDateTime.now());

        resumeMapper.insert(resume);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(EditResumeBO editResumeBO) {

        Resume resume = new Resume();
        BeanUtil.copyProperties(editResumeBO, resume);

        resume.setUpdatedTime(LocalDateTime.now());

        LambdaQueryWrapper<Resume> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Resume::getId, editResumeBO.getId());
        // 补充校验, 拿到当前登录人的id, 防止其他用户篡改
        wrapper.eq(Resume::getUserId, editResumeBO.getUserId());

        int res = resumeMapper.update(resume, wrapper);
        if (res != 1) {
            throw new CustomException("简历不存在");
        }
    }

    @Override
    public ResumeVO queryMyResume(String userId) {

        LambdaQueryWrapper<Resume> resumeWrapper = Wrappers.lambdaQuery();
        resumeWrapper.eq(Resume::getUserId, userId);
        List<Resume> resumeList = resumeMapper.selectList(resumeWrapper);
        if (CollUtil.isEmpty(resumeList)) {
            throw new CustomException("简历不存在");
        }

        Resume resume = resumeList.get(0);

        ResumeVO vo = BeanUtil.copyProperties(resume, ResumeVO.class);

        List<ResumeEducation> resumeEducationList = resumeEducationService.findByUserId(userId, resume.getId());

        vo.setEducationList(resumeEducationList);


        List<ResumeProjectExp> resumeProjectExpList = resumeProjectExpService.findByUserId(userId, resume.getId());
        vo.setProjectExpList(resumeProjectExpList);

        List<ResumeWorkExp> resumeWorkExpList = resumeWorkExpService.findByUserId(userId, resume.getId());

        vo.setWorkExpList(resumeWorkExpList);

        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editWorkExp(EditWorkExpBO editWorkExpBO) {


        ResumeWorkExp entity = BeanUtil.copyProperties(editWorkExpBO, ResumeWorkExp.class);
        entity.setUpdatedTime(LocalDateTime.now());
        if (CharSequenceUtil.isBlank(editWorkExpBO.getId())) {
            entity.setCreateTime(LocalDateTime.now());
            resumeWorkExpService.save(entity);
            return;
        }

        resumeWorkExpService.update(entity);

    }

    @Override
    public ResumeWorkExp getWorkExp(String workExpId, String userId) {

        ResumeWorkExp workExp = resumeWorkExpService.getWorkExp(workExpId, userId);
        return workExp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWorkExp(String workExpId, String userId) {

        resumeWorkExpService.delete(workExpId, userId);


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editProjectExp(EditProjectExpBO editProjectExpBO) {


        ResumeProjectExp resumeProjectExp = new ResumeProjectExp();
        BeanUtil.copyProperties(editProjectExpBO, resumeProjectExp);

        resumeProjectExp.setUpdatedTime(LocalDateTime.now());

        if (StrUtil.isBlank(resumeProjectExp.getId())) {
            resumeProjectExp.setCreateTime(LocalDateTime.now());
            resumeProjectExpMapper.insert(resumeProjectExp);
        } else {
            LambdaQueryWrapper<ResumeProjectExp> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ResumeProjectExp::getId, resumeProjectExp.getId());
            wrapper.eq(ResumeProjectExp::getUserId, resumeProjectExp.getUserId());
            wrapper.eq(ResumeProjectExp::getResumeId, resumeProjectExp.getResumeId());

            resumeProjectExpMapper.update(resumeProjectExp, wrapper);
        }

    }

    @Override
    public ResumeProjectExp getProjectExp(String projectExpId, String userId) {

        LambdaQueryWrapper<ResumeProjectExp> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeProjectExp::getId, projectExpId);
        wrapper.eq(ResumeProjectExp::getUserId, userId);

        List<ResumeProjectExp> resumeProjectExpList = resumeProjectExpMapper.selectList(wrapper);
        if (CollUtil.isEmpty(resumeProjectExpList)) {
            return null;
        }

        return resumeProjectExpList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProjectExp(String projectExpId, String userId) {

        LambdaQueryWrapper<ResumeProjectExp> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeProjectExp::getId, projectExpId);
        wrapper.eq(ResumeProjectExp::getUserId, userId);

        int res = resumeProjectExpMapper.delete(wrapper);
        if (res != 1) {
            throw new CustomException("删除失败");
        }
    }

    @Override
    public void editEducation(EditEducationBO editEducationBO) {


        ResumeEducation resumeEducation = new ResumeEducation();
        BeanUtil.copyProperties(editEducationBO, resumeEducation);

        resumeEducation.setUpdatedTime(LocalDateTime.now());

        if (StrUtil.isBlank(resumeEducation.getId())) {
            resumeEducation.setCreateTime(LocalDateTime.now());
            resumeEducationMapper.insert(resumeEducation);
        } else {
            LambdaQueryWrapper<ResumeEducation> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ResumeEducation::getId, resumeEducation.getId());
            wrapper.eq(ResumeEducation::getUserId, resumeEducation.getUserId());
            wrapper.eq(ResumeEducation::getResumeId, resumeEducation.getResumeId());

            resumeEducationMapper.update(resumeEducation, wrapper);
        }

    }

    @Override
    public ResumeEducation getEducation(String eduId, String userId) {


        LambdaQueryWrapper<ResumeEducation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeEducation::getId, eduId);
        wrapper.eq(ResumeEducation::getUserId, userId);

        List<ResumeEducation> resumeEducationList = resumeEducationMapper.selectList(wrapper);
        if (CollUtil.isEmpty(resumeEducationList)) {
            return null;
        }

        return resumeEducationList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteEducation(String eduId, String userId) {


        LambdaQueryWrapper<ResumeEducation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeEducation::getId, eduId);
        wrapper.eq(ResumeEducation::getUserId, userId);

        int res = resumeEducationMapper.delete(wrapper);
        if (res != 1) {
            throw new CustomException("删除失败");
        }
    }

    @Override
    public void editJobExpect(EditResumeExpectBO editResumeExpectBO) {

        ResumeExpect resumeExpect = new ResumeExpect();
        BeanUtil.copyProperties(editResumeExpectBO, resumeExpect);

        resumeExpect.setUpdatedTime(LocalDateTime.now());

        if (CharSequenceUtil.isBlank(editResumeExpectBO.getId())) {
            resumeExpect.setCreateTime(LocalDateTime.now());
            resumeExpectMapper.insert(resumeExpect);
        } else {
            LambdaQueryWrapper<ResumeExpect> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ResumeExpect::getId, editResumeExpectBO.getId());
            wrapper.eq(ResumeExpect::getUserId, editResumeExpectBO.getUserId());
            wrapper.eq(ResumeExpect::getResumeId, editResumeExpectBO.getResumeId());
            resumeExpectMapper.update(resumeExpect, wrapper);
        }
    }

    @Override
    public List<ResumeExpect> getMyResumeExpectList(String resumeId, String userId) {

        LambdaQueryWrapper<ResumeExpect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeExpect::getResumeId, resumeId);
        wrapper.eq(ResumeExpect::getUserId, userId);
        wrapper.orderByDesc(ResumeExpect::getUpdatedTime);

        return resumeExpectMapper.selectList(wrapper);
    }

    @Override
    public void deleteMyResumeExpect(String resumeExpectId, String userId) {

        LambdaQueryWrapper<ResumeExpect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeExpect::getId, resumeExpectId);
        wrapper.eq(ResumeExpect::getUserId, userId);

        int res = resumeExpectMapper.delete(wrapper);

        if (res != 1) {
            throw new CustomException("删除失败");
        }
    }

    @Override
    public void refreshResume(String resumeId, String userId) {


        EditResumeBO editResumeBO = new EditResumeBO();
        editResumeBO.setId(resumeId);
        editResumeBO.setUserId(userId);
        editResumeBO.setRefreshTime(LocalDateTime.now());

        this.modify(editResumeBO);
    }

    @Override
    public PagedGridResult searchResumes(SearchResumeBO searchResumeBO, Integer page, Integer limit) {

        //
        //String basicTitle = searchResumeBO.getBasicTitle();
        //String jobType = searchResumeBO.getJobType();
        //Integer beginAge = searchResumeBO.getBeginAge();
        //Integer endAge = searchResumeBO.getEndAge();
        //Integer sex = searchResumeBO.getSex();
        //Integer activeTimes = searchResumeBO.getActiveTimes();
        //Integer beginWorkExpYears = searchResumeBO.getBeginWorkExpYears();
        //Integer endWorkExpYears = searchResumeBO.getEndWorkExpYears();
        //String edu = searchResumeBO.getEdu();
        //List<String> eduList = searchResumeBO.getEduList();
        //Integer beginSalary = searchResumeBO.getBeginSalary();
        //Integer endSalary = searchResumeBO.getEndSalary();
        //String jobStatus = searchResumeBO.getJobStatus();
        //
        //
        SearchResumeDTO queryDTO = BeanUtil.copyProperties(searchResumeBO, SearchResumeDTO.class);
        queryDTO.setPage(page);
        queryDTO.setLimit(limit);
        //
        //PageHelper.startPage(page, limit);
        //List<SearchResumeVO> voList = resumeMapper.searchResumes(queryDTO);
        //
        //return PagedGridResult.build(voList, page);
        R<PagedGridResult> rSearch = resumeSearchRemoteApi.search(queryDTO);
        return rSearch.getData();
    }

    @Override
    public void transformAndFlushToEs(String userId) {

        ResumeEsCreateDTO baseCreateDTO = new ResumeEsCreateDTO();
        baseCreateDTO.setUserId(userId);

        // 查询用户简历相关信息
        ResumeVO resumeVO = queryMyResume(userId);

        // 远程调用查询用户信息
        UserVO userVO = getUserInfoVO(userId);


        // 填充信息
        baseCreateDTO.setResumeId(resumeVO.getId());
        baseCreateDTO.setNickname(userVO.getNickname());
        baseCreateDTO.setSex(userVO.getSex());
        baseCreateDTO.setBirthday(userVO.getBirthday());
        long age = LocalDateTimeUtil.between(userVO.getBirthday().atStartOfDay(), LocalDateTime.now(), ChronoUnit.YEARS);
        baseCreateDTO.setAge((int) age);

        // 拿到最近的工作信息
        List<ResumeWorkExp> workExpList = resumeVO.getWorkExpList();
        Optional<ResumeWorkExp> lastWorkOpt = workExpList.stream().max(Comparator.comparing(ResumeWorkExp::getBeginDate));
        ResumeWorkExp lastWork = lastWorkOpt.get();
        baseCreateDTO.setCompanyName(lastWork.getCompanyName());
        baseCreateDTO.setPosition(lastWork.getPosition());
        baseCreateDTO.setIndustry(lastWork.getIndustry());

        // 获取最近一次教育经历
        List<ResumeEducation> educationList = resumeVO.getEducationList();
        ResumeEducation lastEducation = educationList.stream().max(Comparator.comparing(ResumeEducation::getBeginDate)).get();
        baseCreateDTO.setSchool(lastEducation.getSchool());
        baseCreateDTO.setEducation(lastEducation.getEducation());
        baseCreateDTO.setMajor(lastEducation.getMajor());

        LocalDate startWorkDate = userVO.getStartWorkDate();
        long workYears = LocalDateTimeUtil.between(startWorkDate.atStartOfDay(), LocalDateTime.now(), ChronoUnit.YEARS);
        baseCreateDTO.setWorkYears((int) workYears);
        baseCreateDTO.setSkills(resumeVO.getSkills());
        baseCreateDTO.setAdvantage(resumeVO.getAdvantage());
        baseCreateDTO.setAdvantageHtml(resumeVO.getAdvantageHtml());
        baseCreateDTO.setCredentials(resumeVO.getCredentials());
        baseCreateDTO.setJobStatus(resumeVO.getStatus());
        baseCreateDTO.setRefreshTime(resumeVO.getRefreshTime());


        // 每个求职期望对应一份简历信息
        List<ResumeExpect> expectList = getMyResumeExpectList(resumeVO.getId(), userId);

        List<ResumeEsCreateDTO> createDTOList = new ArrayList<>();
        for (ResumeExpect resumeExpect : expectList) {

            ResumeEsCreateDTO createDTO = new ResumeEsCreateDTO();
            BeanUtil.copyProperties(baseCreateDTO, createDTO);
            createDTO.setId(resumeExpect.getId());
            createDTO.setResumeExpectId(resumeExpect.getId());
            createDTO.setJobType(resumeExpect.getJobName());
            createDTO.setCity(resumeExpect.getCity());
            createDTO.setBeginSalary(resumeExpect.getBeginSalary());
            createDTO.setEndSalary(resumeExpect.getEndSalary());
            createDTOList.add(createDTO);
        }

        resumeSearchRemoteApi.batchUpdate(createDTOList);
    }

    @Override
    public PagedGridResult pagedReadResumeRecordList(String hrId, Integer page, Integer pageSize) {


        LambdaQueryWrapper<ResumeRead> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeRead::getUserId, hrId);

        PageHelper.startPage(page + 1, pageSize);
        List<ResumeRead> rList = resumeReadMapper.selectList(wrapper);
        PageInfo<ResumeRead> midPage = new PageInfo<>(rList);

        // 拿到id
        List<String> resumeExpectedIdList = midPage.getList().stream().map(ResumeRead::getResumeExpectId).collect(Collectors.toList());


        R<List<ResumeEsVO>> listR = resumeSearchRemoteApi.searchByIds(resumeExpectedIdList);

        Map<String, ResumeRead> cache = CollStreamUtil.toIdentityMap(rList, ResumeRead::getResumeExpectId);
        // 时间匹配
        listR.getData().forEach(e -> {
            String key = e.getResumeExpectId();
            ResumeRead resumeRead = cache.get(key);
            e.setHrReadResumeTime(resumeRead.getCreateTime());
        });


        return PagedGridResult.build(listR.getData(), page);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void hrLookCand(ResumeLookBO resumeLookBO) {

        ResumeLook resumeLook = BeanUtil.copyProperties(resumeLookBO, ResumeLook.class);
        resumeLook.setCreateTime(LocalDateTime.now());
        resumeLook.setUpdatedTime(LocalDateTime.now());

        resumeLookMapper.insert(resumeLook);
    }

    @Override
    public Long getWhoLookMeCount(String candUserId) {

        LambdaQueryWrapper<ResumeLook> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeLook::getCandUserId, candUserId);
        return resumeLookMapper.selectCount(wrapper);
    }

    @Override
    public PagedGridResult pagedWhoLookMe(String candUserId, Integer page, Integer pageSize) {

        if (page == 0) {
            page = 1;
        }

        PageHelper.startPage(page, pageSize);

        LambdaQueryWrapper<ResumeLook> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ResumeLook::getCandUserId, candUserId);
        List<ResumeLook> resumeLooks = resumeLookMapper.selectList(wrapper);

        return PagedGridResult.build(resumeLooks, page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void followHr(FollowHr followHr) {

        followHr.setCreateTime(LocalDateTime.now());
        followHr.setUpdatedTime(LocalDateTime.now());

        followHrMapper.insert(followHr);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unfollowHr(String hrId, String candUserId) {

        LambdaQueryWrapper<FollowHr> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FollowHr::getHrId, hrId);
        wrapper.eq(FollowHr::getCandUserId, candUserId);

        int res = followHrMapper.delete(wrapper);
        if (res <= 0) {
            throw new CustomException("数据不存在");
        }
    }

    @Override
    public Boolean doesCandFollowHr(String hrId, String candUserId) {

        LambdaQueryWrapper<FollowHr> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FollowHr::getHrId, hrId);
        wrapper.eq(FollowHr::getCandUserId, candUserId);

        Long res = followHrMapper.selectCount(wrapper);
        return res >= 1;
    }

    @Override
    public Long getCandFollowHrCount(String candUserId) {

        LambdaQueryWrapper<FollowHr> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FollowHr::getCandUserId, candUserId);

        return followHrMapper.selectCount(wrapper);
    }

    @Override
    public PagedGridResult pagedCandFollowHr(String candUserId, Integer page, Integer pageSize) {

        if (page == 0) {
            page = 1;
        }

        LambdaQueryWrapper<FollowHr> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FollowHr::getCandUserId, candUserId);
        PageHelper.startPage(page, pageSize);

        List<FollowHr> list = followHrMapper.selectList(wrapper);

        return PagedGridResult.build(list, page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCollectJob(String candUserId, String jobId) {

        JobCollect jobCollect = new JobCollect();
        jobCollect.setCandUserId(candUserId);
        jobCollect.setJobId(jobId);
        jobCollect.setCreateTime(LocalDateTime.now());
        jobCollect.setUpdatedTime(LocalDateTime.now());

        jobCollectMapper.insert(jobCollect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeCollectJob(String candUserId, String jobId) {


        LambdaQueryWrapper<JobCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(JobCollect::getCandUserId, candUserId);
        wrapper.eq(JobCollect::getJobId, jobId);

        int res = jobCollectMapper.delete(wrapper);
        if (res <= 0) {
            throw new CustomException("数据不存在");
        }
    }

    @Override
    public Boolean isCandCollectJob(String candUserId, String jobId) {

        LambdaQueryWrapper<JobCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(JobCollect::getCandUserId, candUserId);
        wrapper.eq(JobCollect::getJobId, jobId);

        Long count = jobCollectMapper.selectCount(wrapper);
        return count >= 1;
    }


    private UserVO getUserInfoVO(String userId) {

        R<UserVO> userVOR = userInfoRemoteApi.get(userId);
        return userVOR.getData();
    }

}
