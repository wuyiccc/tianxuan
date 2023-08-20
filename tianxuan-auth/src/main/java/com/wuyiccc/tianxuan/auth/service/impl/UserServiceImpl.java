package com.wuyiccc.tianxuan.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyiccc.tianxuan.auth.mapper.UserMapper;
import com.wuyiccc.tianxuan.auth.service.UserService;
import com.wuyiccc.tianxuan.common.enumeration.SexEnum;
import com.wuyiccc.tianxuan.common.enumeration.ShowWhichNameEnum;
import com.wuyiccc.tianxuan.common.enumeration.UserRoleEnum;
import com.wuyiccc.tianxuan.common.util.DesensitizationUtils;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import com.wuyiccc.tianxuan.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2023/6/27 22:43
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    private String defaultFaceImgUrl = "http://www.wuyiccc.com/imgs/avatar2.jpg";

    @Override
    public User queryUserByMobile(String mobile) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMobile, mobile);
        return userMapper.selectOne(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User createUser(String mobile) {
        User user = new User();
        user.setMobile(mobile);
        user.setNickname("用户" + DesensitizationUtils.commonDisplay(mobile));
        user.setRealName("用户" + DesensitizationUtils.commonDisplay(mobile));
        user.setShowWhichName(ShowWhichNameEnum.NICKNAME.code);

        user.setSex(SexEnum.SECRET.code);
        user.setFace(defaultFaceImgUrl);
        user.setEmail("");

        user.setBirthday(LocalDateUtils.parseLocalDate("1980-01-01", LocalDateUtils.DATE_PATTERN));
        user.setCountry("中国");
        user.setProvince("");
        user.setCity("");
        user.setDistrict("");
        user.setDescription("这家伙很懒, 什么都没留下");

        // 默认使用注册当天的日期
        user.setStartWorkDate(LocalDate.now());
        user.setPosition("工程师");
        user.setRole(UserRoleEnum.CANDIDATE.code);
        user.setHrInWhichCompanyId("");
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());

        userMapper.insert(user);

        return user;
    }
}
