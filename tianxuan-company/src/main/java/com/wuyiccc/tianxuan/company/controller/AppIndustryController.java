package com.wuyiccc.tianxuan.company.controller;

import cn.hutool.json.JSONUtil;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.company.service.IndustryService;
import com.wuyiccc.tianxuan.pojo.Industry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/12/30 22:55
 * app
 */
@RestController
@RequestMapping("/appIndustry")
public class AppIndustryController {

    @Resource
    private IndustryService industryService;

    @Resource
    private RedisUtils redisUtils;


    @GetMapping("/getTopList")
    public CommonResult<List<Industry>> getTopIndustryList() {

        // 先从redis中查询, 如果没有, 再从db中查询后并放入redis中
        String topIndustryListStr = redisUtils.get(BaseInfoProperties.TOP_INDUSTRY_LIST);

        List<Industry> resList = null;

        if (Objects.nonNull(topIndustryListStr)) {
            resList = JSONUtil.toList(topIndustryListStr, Industry.class);
        } else {
            // 即使查出来为null也需要设置, 防止缓存穿透
            resList = industryService.getTopIndustryList();
            redisUtils.set(BaseInfoProperties.TOP_INDUSTRY_LIST, JSONUtil.toJsonStr(resList), 10 * 60);
        }
        return CommonResult.ok(resList);
    }


    @GetMapping("/getThirdListByTop/{topIndustryId}")
    public CommonResult<List<Industry>> getThirdIndustryListByTop(@PathVariable("topIndustryId") String topIndustryId) {

        String resListStr = redisUtils.get(BaseInfoProperties.THIRD_INDUSTRY_LIST + ":byTopId:" + topIndustryId);

        List<Industry> resList;
        if (Objects.nonNull(resListStr)) {
            resList = JSONUtil.toList(resListStr, Industry.class);
        } else {
            resList = industryService.getThirdIndustryListByTop(topIndustryId);

            redisUtils.set(BaseInfoProperties.THIRD_INDUSTRY_LIST + ":byTopId:" + topIndustryId, JSONUtil.toJsonStr(resList), 10 * 60);
        }

        return CommonResult.ok(resList);
    }



}
