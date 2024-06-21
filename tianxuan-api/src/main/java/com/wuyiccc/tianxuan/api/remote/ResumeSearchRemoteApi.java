package com.wuyiccc.tianxuan.api.remote;

import com.wuyiccc.tianxuan.api.remote.fallback.ResumeSearchRemoteApiFallback;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.dto.ResumeEsCreateDTO;
import com.wuyiccc.tianxuan.pojo.dto.SearchResumeDTO;
import com.wuyiccc.tianxuan.pojo.vo.ResumeEsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/16 16:59
 */
@FeignClient(name = "tianxuan-search", path = "/resumeSearch", fallback = ResumeSearchRemoteApiFallback.class)
public interface ResumeSearchRemoteApi {

    @PostMapping("/batchUpdate")
    public R<String> batchUpdate(@RequestBody List<ResumeEsCreateDTO> createDTOList);

    @PostMapping("/search")
    public R<PagedGridResult> search(@RequestBody SearchResumeDTO searchResumeDTO);

    @PostMapping("/searchByIds")
    public R<List<ResumeEsVO>> searchByIds(@RequestBody List<String> resumeExpectIdList);
}
