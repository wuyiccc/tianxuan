package com.wuyiccc.tianxuan.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.wuyiccc.tianxuan.auth.mapper.AdminMapper;
import com.wuyiccc.tianxuan.auth.service.AdminService;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.bo.AdminLoginBO;
import com.wuyiccc.tianxuan.pojo.vo.AdminUserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.wuyiccc.tianxuan.common.base.BaseInfoProperties.TOKEN_ADMIN_PREFIX;

/**
 * @author wuyiccc
 * @date 2023/8/20 11:27
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {



    @Autowired
    private AdminMapper adminMapper;


    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public AdminUserLoginVO login(AdminLoginBO adminLoginBO) {


        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, adminLoginBO.getUsername());
        Admin admin = adminMapper.selectOne(wrapper);

        boolean checkPassword = BCrypt.checkpw(adminLoginBO.getPassword(), admin.getPassword());

        if (!checkPassword) {
            throw new CustomException("密码错误");
        }


        String uToken = jwtUtils.createJWTWithPrefix(new Gson().toJson(admin), TOKEN_ADMIN_PREFIX);

        AdminUserLoginVO adminUserLoginVO = new AdminUserLoginVO();

        adminUserLoginVO.setToken(uToken);
        adminUserLoginVO.setAdmin(admin);
        adminUserLoginVO.setId(admin.getId());
        return adminUserLoginVO;
    }

    @Override
    public void createAdmin(Admin admin) {

        admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));
        admin.setSlat("");
        admin.setFace("");
        admin.setRemark("");
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdatedTime(LocalDateTime.now());

        adminMapper.insert(admin);
    }
}
