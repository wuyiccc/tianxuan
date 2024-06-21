package com.wuyiccc.tianxuan.search.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.dto.ResumeEsCreateDTO;
import com.wuyiccc.tianxuan.pojo.dto.SearchResumeDTO;
import com.wuyiccc.tianxuan.pojo.vo.ResumeEsVO;
import com.wuyiccc.tianxuan.search.mapper.es.ResumeEsMapper;
import com.wuyiccc.tianxuan.search.pojo.es.ResumeEsEntity;
import com.wuyiccc.tianxuan.search.service.ResumeEsService;
import com.wuyiccc.tianxuan.search.utils.EasyEsUtils;
import lombok.extern.slf4j.Slf4j;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/16 11:08
 */
@Slf4j
@Service
public class ResumeEsServiceImpl implements ResumeEsService {

    @Resource
    private ResumeEsMapper resumeEsMapper;


    @Override
    public void batchUpdate(List<ResumeEsCreateDTO> createDTOList) {


        List<ResumeEsEntity> esEntityList = BeanUtil.copyToList(createDTOList, ResumeEsEntity.class);
        resumeEsMapper.insertBatch(esEntityList, EasyEsUtils.getAliasName(ResumeEsEntity.class));
    }

    @Override
    public PagedGridResult search(SearchResumeDTO searchResumeDTO) {

        LambdaEsQueryWrapper<ResumeEsEntity> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.indexName(EasyEsUtils.getAliasName(ResumeEsEntity.class));

        if (CharSequenceUtil.isNotBlank(searchResumeDTO.getBasicTitle())) {
            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.or(builder -> builder.like(ResumeEsEntity::getNickname, searchResumeDTO.getBasicTitle()))
                        .or(builder -> builder.like(ResumeEsEntity::getAdvantage, searchResumeDTO.getBasicTitle()))
                        .or(builder -> builder.like(ResumeEsEntity::getCredentials, searchResumeDTO.getBasicTitle()))
                        .or(builder -> builder.like(ResumeEsEntity::getSkills, searchResumeDTO.getBasicTitle()))
                ;
            });
        }

        if (CharSequenceUtil.isNotBlank(searchResumeDTO.getJobType())) {
            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.eq(ResumeEsEntity::getJobType, searchResumeDTO.getJobType());
            });
        }


        Integer beginAge = searchResumeDTO.getBeginAge();
        Integer endAge = searchResumeDTO.getEndAge();

        if (Objects.nonNull(beginAge)) {
            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.ge(ResumeEsEntity::getAge, beginAge);
            });
        }
        if (Objects.nonNull(endAge)) {
            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.le(ResumeEsEntity::getAge, endAge);
            });
        }

        Integer sex = searchResumeDTO.getSex();
        if (Objects.nonNull(sex) && sex >= 0) {
            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.eq(ResumeEsEntity::getSex, sex);
            });
        }

        Integer beginWorkExpYears = searchResumeDTO.getBeginWorkExpYears();
        Integer endWorkExpYears = searchResumeDTO.getEndWorkExpYears();
        if (Objects.nonNull(beginWorkExpYears)) {
            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.ge(ResumeEsEntity::getWorkYears, beginWorkExpYears);
            });
        }
        if (Objects.nonNull(endWorkExpYears)) {
            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.le(ResumeEsEntity::getWorkYears, endWorkExpYears);
            });
        }

        if (CollUtil.isNotEmpty(searchResumeDTO.getEduList())) {

            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.in(ResumeEsEntity::getEducation, searchResumeDTO.getEduList());
            });
        }

        Integer beginSalary = searchResumeDTO.getBeginSalary();
        Integer endSalary = searchResumeDTO.getEndSalary();
        if (Objects.nonNull(beginSalary) && beginSalary > 0) {
            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.ge(ResumeEsEntity::getEndSalary, beginSalary);
            });
        }
        if (Objects.nonNull(endSalary) && endSalary < 0) {
            wrapper.and(boolQueryBuilder -> {
                boolQueryBuilder.le(ResumeEsEntity::getBeginSalary, endSalary);
            });
        }
        //
        String jobStatus = searchResumeDTO.getJobStatus();
        if (CharSequenceUtil.isNotBlank(jobStatus)) {
            wrapper.and(qb -> {
                qb.eq(ResumeEsEntity::getJobStatus, jobStatus);
            });
        }


        EsPageInfo<ResumeEsEntity> resumeEsEntityEsPageInfo = resumeEsMapper.pageQuery(wrapper, searchResumeDTO.getPage(), searchResumeDTO.getLimit());

        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(resumeEsEntityEsPageInfo.getPageNum());
        pagedGridResult.setTotal(resumeEsEntityEsPageInfo.getPages());
        pagedGridResult.setRecords(resumeEsEntityEsPageInfo.getTotal());
        pagedGridResult.setRows(resumeEsEntityEsPageInfo.getList());

        return pagedGridResult;
    }

    @Override
    public List<ResumeEsVO> searchByIds(List<String> resumeExpectIdList) {

        if (CollUtil.isEmpty(resumeExpectIdList)) {
            return ListUtil.empty();
        }


        List<ResumeEsEntity> entityList = resumeEsMapper.selectBatchIds(resumeExpectIdList);

        return BeanUtil.copyToList(entityList, ResumeEsVO.class);
    }


}
