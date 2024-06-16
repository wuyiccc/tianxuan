package com.wuyiccc.tianxuan.search.service;

import com.wuyiccc.tianxuan.pojo.dto.ResumeEsCreateDTO;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/16 11:08
 */
public interface ResumeEsService {

    void batchUpdate(List<ResumeEsCreateDTO> createDTOList);
}
