package com.wuyiccc.tianxuan.api.feign.fallback;

import cn.hutool.core.collection.ListUtil;
import com.wuyiccc.tianxuan.api.feign.UserInfoInnerServiceFeign;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/15 10:57
 * feign远程服务出现问题的时候进行兜底处理
 */
@Component
public class UserInfoInnerServiceFeignFallback implements UserInfoInnerServiceFeign {


    @Override
    public CommonResult<Long> getCountsByCompanyId(String companyId) {
        return null;
    }

    @Override
    public CommonResult<User> bindingHRToCompany(String hrUserId, String realname, String companyId) {
        return null;
    }

    @Override
    public CommonResult<UserVO> get(String userId) {
        return null;
    }

    @Override
    public CommonResult<String> changeUserToHR(String hrUserId) {
        return null;
    }

    @Override
    public CommonResult<List<UserVO>> getList(List<String> userIdList) {

        return CommonResult.ok(ListUtil.empty());
    }
}
