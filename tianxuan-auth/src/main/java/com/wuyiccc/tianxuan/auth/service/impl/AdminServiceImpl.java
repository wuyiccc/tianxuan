package com.wuyiccc.tianxuan.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyiccc.tianxuan.auth.mapper.AdminMapper;
import com.wuyiccc.tianxuan.auth.service.AdminService;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.MD5Utils;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.bo.AdminBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/8/22 21:55
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public Admin adminLogin(AdminBO adminBO) {

        LambdaQueryWrapper<Admin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Admin::getUsername, adminBO.getUsername());
        Admin admin = adminMapper.selectOne(wrapper);

        if (Objects.isNull(admin)) {
            throw new CustomException(ResponseStatusEnum.ADMIN_LOGIN_ERROR);
        }

        String slat = admin.getSlat();
        String md5str = MD5Utils.encrypt(adminBO.getPassword(), slat);
        if (md5str.equalsIgnoreCase(admin.getPassword())) {
            throw new CustomException(ResponseStatusEnum.ADMIN_LOGIN_ERROR);
        }

        return admin;
    }
}
