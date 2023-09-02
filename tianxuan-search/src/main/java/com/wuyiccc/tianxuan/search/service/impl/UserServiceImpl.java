package com.wuyiccc.tianxuan.search.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyiccc.tianxuan.search.mapper.UserMapper;
import com.wuyiccc.tianxuan.search.pojo.User;
import com.wuyiccc.tianxuan.search.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @searchor wuyiccc
 * @date 2023/9/2 19:17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        return user;
    }
}
