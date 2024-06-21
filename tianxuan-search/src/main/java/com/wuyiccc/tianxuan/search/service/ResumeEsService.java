package com.wuyiccc.tianxuan.search.service;

import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.dto.ResumeEsCreateDTO;
import com.wuyiccc.tianxuan.pojo.dto.SearchResumeDTO;
import com.wuyiccc.tianxuan.pojo.vo.ResumeEsVO;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/16 11:08
 */
public interface ResumeEsService {

    void batchUpdate(List<ResumeEsCreateDTO> createDTOList);

    PagedGridResult search(SearchResumeDTO searchResumeDTO);

    List<ResumeEsVO> searchByIds(List<String> resumeExpectIdList);
}
