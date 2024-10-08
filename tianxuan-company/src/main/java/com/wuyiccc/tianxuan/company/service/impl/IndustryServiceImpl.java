package com.wuyiccc.tianxuan.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.company.mapper.IndustryMapper;
import com.wuyiccc.tianxuan.company.service.IndustryService;
import com.wuyiccc.tianxuan.pojo.Industry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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

    @Override
    public List<Industry> getTopIndustryList() {

        LambdaQueryWrapper<Industry> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Industry::getFatherId, "0");
        wrapper.orderByAsc(Industry::getSort);
        return industryMapper.selectList(wrapper);
    }

    @Override
    public List<Industry> getChildrenIndustryList(String industryId) {

        LambdaQueryWrapper<Industry> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Industry::getFatherId, industryId);
        wrapper.orderByAsc(Industry::getSort);
        return industryMapper.selectList(wrapper);
    }

    @Override
    public void updateNode(Industry industry) {


        industryMapper.updateById(industry);
    }

    @Override
    public Industry getById(String industryId) {

        return industryMapper.selectById(industryId);
    }

    @Override
    public Long getChildrenIndustryCounts(String fatherId) {

        LambdaQueryWrapper<Industry> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Industry::getFatherId, fatherId);

        return industryMapper.selectCount(wrapper);
    }

    @Override
    public void deleteNode(String industryId) {

        industryMapper.deleteById(industryId);
    }

    @Override
    public List<Industry> getThirdIndustryListByTop(String topIndustryId) {

        return industryMapper.getThirdIndustryListByTop(topIndustryId);
    }

    @Override
    public String getTopIdBySecondId(String id) {

        LambdaQueryWrapper<Industry> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Industry::getId, id);

        Industry secondIndustry = industryMapper.selectOne(wrapper);

        return secondIndustry.getFatherId();
    }

}
