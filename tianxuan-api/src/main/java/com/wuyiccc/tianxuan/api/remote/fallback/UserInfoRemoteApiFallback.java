package com.wuyiccc.tianxuan.api.remote.fallback;

import cn.hutool.core.collection.ListUtil;
import com.wuyiccc.tianxuan.api.remote.UserInfoRemoteApi;
import com.wuyiccc.tianxuan.common.result.R;
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
public class UserInfoRemoteApiFallback implements UserInfoRemoteApi {


    @Override
    public R<Long> getCountsByCompanyId(String companyId) {
        return null;
    }

    @Override
    public R<User> bindingHRToCompany(String hrUserId, String realname, String companyId) {
        return null;
    }

    @Override
    public R<UserVO> get(String userId) {
        return null;
    }

    @Override
    public R<String> changeUserToHR(String hrUserId) {
        return null;
    }

    @Override
    public R<List<UserVO>> getList(List<String> userIdList) {

        return R.ok(ListUtil.empty());
    }
}
