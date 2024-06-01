package com.wuyiccc.tianxuan.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyiccc.tianxuan.pojo.Resume;
import com.wuyiccc.tianxuan.pojo.dto.SearchResumeDTO;
import com.wuyiccc.tianxuan.pojo.vo.SearchResumeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/18 20:32
 */
@Repository
public interface ResumeMapper extends BaseMapper<Resume> {
    List<SearchResumeVO> searchResumes(@Param("queryDTO") SearchResumeDTO queryDTO);
}
