package com.wuyiccc.tianxuan.search.service.impl;

import com.wuyiccc.tianxuan.search.mapper.es.ResumeEsMapper;
import com.wuyiccc.tianxuan.search.service.ResumeEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/16 11:08
 */
@Slf4j
@Service
public class ResumeEsServiceImpl implements ResumeEsService {

    @Resource
    private ResumeEsMapper resumeEsMapper;

    
}
