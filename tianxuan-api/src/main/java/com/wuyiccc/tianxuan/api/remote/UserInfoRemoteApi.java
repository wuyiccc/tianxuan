package com.wuyiccc.tianxuan.api.remote;

import com.wuyiccc.tianxuan.api.remote.fallback.UserInfoRemoteApiFallback;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/1/2 21:43
 */
@FeignClient(value = "tianxuan-user", fallback = UserInfoRemoteApiFallback.class)
public interface UserInfoRemoteApi {

    @PostMapping("/userInfoInner/getCountsByCompanyId")
    R<Long> getCountsByCompanyId(@RequestParam("companyId") String companyId);


    @PostMapping("/userInfoInner/bindingHRToCompany")
    R<User> bindingHRToCompany(
            @RequestParam("hrUserId") String hrUserId,
            @RequestParam("realname") String realname,
            @RequestParam("companyId") String companyId
    );

    @PostMapping("/userInfoInner/get")
    R<UserVO> get(@RequestParam("userId") String userId);

    @PostMapping("/userInfoInner/changeUserToHR")
    R<String> changeUserToHR(@RequestParam("hrUserId") String hrUserId);

    @PostMapping("/userInfoInner/getList")
    R<List<UserVO>> getList(@RequestBody List<String> userIdList);

}
