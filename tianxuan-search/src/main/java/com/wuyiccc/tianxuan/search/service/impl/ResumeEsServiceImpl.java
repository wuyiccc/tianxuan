package com.wuyiccc.tianxuan.search.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.wuyiccc.tianxuan.pojo.dto.ResumeEsCreateDTO;
import com.wuyiccc.tianxuan.search.mapper.es.ResumeEsMapper;
import com.wuyiccc.tianxuan.search.pojo.es.ResumeEsEntity;
import com.wuyiccc.tianxuan.search.service.ResumeEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        resumeEsMapper.insertBatch(esEntityList);
    }


}
