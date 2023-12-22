package com.wuyiccc.tianxuan.user.service.impl;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.bo.ModifyUserBO;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;
import com.wuyiccc.tianxuan.user.mapper.UserMapper;
import com.wuyiccc.tianxuan.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
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
        if (CharSequenceUtil.isBlank(userId)) {
            throw new CustomException(ResponseStatusEnum.USER_INFO_UPDATED_ERROR);
        }

        User newUser = new User();
        BeanUtil.copyProperties(modifyUserBO, newUser);

        newUser.setId(userId);
        newUser.setUpdatedTime(LocalDateTime.now());
        userMapper.updateById(newUser);
    }

    @Override
    public User getById(String id) {

        User user = userMapper.selectById(id);

        return user;
    }


}
