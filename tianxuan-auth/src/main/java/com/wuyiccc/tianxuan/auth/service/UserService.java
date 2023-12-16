package com.wuyiccc.tianxuan.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyiccc.tianxuan.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuyiccc
 * @date 2023/6/27 22:42
 */
public interface UserService extends IService<User> {

    public User queryUserByMobile(String mobile);

    public User createUser(String mobile);

    void getSMSCode(String mobile, HttpServletRequest request);
}
