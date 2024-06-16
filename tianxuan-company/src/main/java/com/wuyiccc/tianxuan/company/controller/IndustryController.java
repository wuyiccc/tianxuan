package com.wuyiccc.tianxuan.company.controller;

import cn.hutool.json.JSONUtil;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.company.service.IndustryService;
import com.wuyiccc.tianxuan.pojo.Industry;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/30 16:14
 * admin服务
 */
@RestController
@RequestMapping("/industry")
public class IndustryController {

    @Resource
    private IndustryService industryService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/createNode")
    public R<String> createNode(@RequestBody Industry industry) {


        // 1. 判断节点是否已经存
        boolean flag = industryService.getIndustryIsExistByName(industry.getName());
        if (flag) {
            throw new CustomException("该行业已经存在, 请重新命名");
        }

        // 节点创建
        industryService.createIndustry(industry);

        resetCache(industry);

        return R.ok();
    }

    @GetMapping("/getTopList")
    public R<List<Industry>> getTopIndustryList() {
        List<Industry> resList = industryService.getTopIndustryList();
        return R.ok(resList);
    }

    @GetMapping("/children/{industryId}")
    public R<List<Industry>> children(@PathVariable("industryId") String industryId) {

        List<Industry> resList = industryService.getChildrenIndustryList(industryId);

        return R.ok(resList);
    }

    @PostMapping("/updateNode")
    public R<String> updateNode(@RequestBody Industry industry) {

        industryService.updateNode(industry);

        resetCache(industry);
        return R.ok();
    }

    @DeleteMapping("/deleteNode/{industryId}")
    public R<String> deleteNode(@PathVariable("industryId") String industryId) {

        Industry industry = industryService.getById(industryId);


        if (industry.getLevel() == 1 || industry.getLevel() == 2) {
            Long count = industryService.getChildrenIndustryCounts(industryId);
            if (count != 0) {
                throw new CustomException("请保证该节点下无任何子节点后再删除");
            }
        }

        industryService.deleteNode(industryId);

        resetCache(industry);

        return R.ok();
    }

    // 重置缓存必须确保前面的更新事务已经提交
    public void  resetCache(Industry industry) {

        if (industry.getLevel() == 1) {

            List<Industry> resList = industryService.getTopIndustryList();
            redisUtils.set(BaseInfoProperties.TOP_INDUSTRY_LIST, JSONUtil.toJsonStr(resList), 10 * 60);
        } else if (industry.getLevel() == 3) {

            String topId = industryService.getTopIdBySecondId(industry.getFatherId());

            List<Industry> resList = industryService.getThirdIndustryListByTop(topId);

            redisUtils.set(BaseInfoProperties.THIRD_INDUSTRY_LIST + ":byTopId:" + topId, JSONUtil.toJsonStr(resList), 10 * 60);
        }
    }
}
