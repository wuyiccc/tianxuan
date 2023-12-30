package com.wuyiccc.tianxuan.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.company.mapper.IndustryMapper;
import com.wuyiccc.tianxuan.company.service.IndustryService;
import com.wuyiccc.tianxuan.pojo.Industry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/12/30 16:18
 */
@Slf4j
@Service
public class IndustryServiceImpl implements IndustryService {


    @Resource
    private IndustryMapper industryMapper;

    @Override
    public boolean getIndustryIsExistByName(String name) {

        LambdaQueryWrapper<Industry> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Industry::getName, name);

        Industry industry = industryMapper.selectOne(wrapper);
        return Objects.nonNull(industry);
    }

    @Override
    public void createIndustry(Industry industry) {

        industryMapper.insert(industry);
    }
}
