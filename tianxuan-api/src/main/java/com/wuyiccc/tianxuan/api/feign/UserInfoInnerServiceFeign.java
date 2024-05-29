package com.wuyiccc.tianxuan.api.feign;

import com.wuyiccc.tianxuan.common.result.CommonResult;
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
@FeignClient("tianxuan-user")
public interface UserInfoInnerServiceFeign {

    @PostMapping("/userInfoInner/getCountsByCompanyId")
    CommonResult<Long> getCountsByCompanyId(@RequestParam("companyId") String companyId);


    @PostMapping("/userInfoInner/bindingHRToCompany")
    CommonResult<User> bindingHRToCompany(
            @RequestParam("hrUserId") String hrUserId,
            @RequestParam("realname") String realname,
            @RequestParam("companyId") String companyId
    );

    @PostMapping("/userInfoInner/get")
    CommonResult<UserVO> get(@RequestParam("userId") String userId);

    @PostMapping("/userInfoInner/changeUserToHR")
    CommonResult<String> changeUserToHR(@RequestParam("hrUserId") String hrUserId);

    @PostMapping("/userInfoInner/getList")
    CommonResult<List<UserVO>> getList(@RequestBody List<String> userIdList);

}
