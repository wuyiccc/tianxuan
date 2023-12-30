package com.wuyiccc.tianxuan.company.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyiccc.tianxuan.pojo.Industry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/30 16:16
 */
public interface IndustryMapper extends BaseMapper<Industry> {
    List<Industry> getThirdIndustryListByTop(@Param("topIndustryId") String topIndustryId);


}
