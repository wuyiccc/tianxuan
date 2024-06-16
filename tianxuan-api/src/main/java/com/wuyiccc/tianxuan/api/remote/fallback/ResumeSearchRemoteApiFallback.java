package com.wuyiccc.tianxuan.api.remote.fallback;

import com.wuyiccc.tianxuan.api.remote.ResumeSearchRemoteApi;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.dto.ResumeEsCreateDTO;
import com.wuyiccc.tianxuan.pojo.dto.SearchResumeDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/16 17:02
 */
@Component
public class ResumeSearchRemoteApiFallback implements ResumeSearchRemoteApi {
    @Override
    public R<String> batchUpdate(List<ResumeEsCreateDTO> createDTOList) {
        return null;
    }

    @Override
    public R<PagedGridResult> search(SearchResumeDTO searchResumeDTO) {
        return null;
    }
}
