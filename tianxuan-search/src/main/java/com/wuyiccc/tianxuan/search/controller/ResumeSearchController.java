package com.wuyiccc.tianxuan.search.controller;

import com.wuyiccc.tianxuan.api.remote.ResumeSearchRemoteApi;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.dto.ResumeEsCreateDTO;
import com.wuyiccc.tianxuan.pojo.dto.SearchResumeDTO;
import com.wuyiccc.tianxuan.search.service.ResumeEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/16 11:09
 */
@RestController
@RequestMapping("/resumeSearch")
@Slf4j
public class ResumeSearchController implements ResumeSearchRemoteApi {

    @Resource
    private ResumeEsService resumeEsService;


    public R<String> batchUpdate(List<ResumeEsCreateDTO> createDTOList) {

        resumeEsService.batchUpdate(createDTOList);
        return R.ok();
    }

    @Override
    public R<PagedGridResult> search(SearchResumeDTO searchResumeDTO) {

        PagedGridResult pagedGridResult = resumeEsService.search(searchResumeDTO);
        return R.ok(pagedGridResult);
    }


}
