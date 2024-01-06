package com.wuyiccc.tianxuan.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.bo.ModifyUserBO;
import com.wuyiccc.tianxuan.user.mapper.UserMapper;
import com.wuyiccc.tianxuan.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2023/12/22 20:54
 */
@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyUserInfo(ModifyUserBO modifyUserBO) {


        String userId = modifyUserBO.getUserId();
        if (StringUtils.isBlank(userId)) {
            throw new CustomException(ResponseStatusEnum.USER_INFO_UPDATED_ERROR);
        }

        User newUser = new User();
        BeanUtils.copyProperties(modifyUserBO, newUser);

        newUser.setId(userId);
        newUser.setUpdatedTime(LocalDateTime.now());
        userMapper.updateById(newUser);
    }

    @Override
    public User getById(String id) {

        User user = userMapper.selectById(id);

        return user;
    }

    @Override
    public Long getCountsByCompanyId(String companyId) {

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getHrInWhichCompanyId, companyId);

        return userMapper.selectCount(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserCompanyId(String hrUserId, String realname, String companyId) {


        User hrUser = new User();
        hrUser.setId(hrUserId);
        hrUser.setRealName(realname);
        hrUser.setHrInWhichCompanyId(companyId);

        hrUser.setUpdatedTime(LocalDateTime.now());

        userMapper.updateById(hrUser);
    }


}
