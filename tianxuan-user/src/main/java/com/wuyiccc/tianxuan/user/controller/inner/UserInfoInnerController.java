package com.wuyiccc.tianxuan.user.controller.inner;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
import com.wuyiccc.tianxuan.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/1/2 21:30
 */
@RestController
@RequestMapping("/userInfoInner")
@Slf4j
public class UserInfoInnerController {

    @Resource
    private UserService userService;

    @Resource
    private JWTUtils jwtUtils;


    @PostMapping("getCountsByCompanyId")
    public R<Long> getCountsByCompanyId(@RequestParam("companyId") String companyId) {

        Long count = userService.getCountsByCompanyId(companyId);
        return R.ok(count);
    }

    @PostMapping("bindingHRToCompany")
    public R<User> bindingHRToCompany(
            @RequestParam("hrUserId") String hrUserId,
            @RequestParam("realname") String realname,
            @RequestParam("companyId") String companyId
    ) {

        userService.updateUserCompanyId(hrUserId, realname, companyId);

        User user = userService.getById(hrUserId);

        return R.ok(user);
    }

    @PostMapping("get")
    public R<UserVO> get(@RequestParam("userId") String userId) {

        User user = userService.getById(userId);
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);

        String uToken = jwtUtils.createJWTWithPrefix(JSONUtil.toJsonStr(user), BaseInfoProperties.TOKEN_USER_PREFIX);
        userVO.setUserToken(uToken);
        return R.ok(userVO);
    }

    @PostMapping("changeUserToHR")
    public R<String> changeUserToHR(@RequestParam("hrUserId") String hrUserId) {

        userService.updateUserToHR(hrUserId);
        return R.ok();
    }

    @PostMapping("/getList")
    public R<List<UserVO>> getList(@RequestBody List<String> userIdList) {

        List<User> userList = userService.getByIds(userIdList);

        List<UserVO> userVOList = BeanUtil.copyToList(userList, UserVO.class);
        return R.ok(userVOList);
    }
}
