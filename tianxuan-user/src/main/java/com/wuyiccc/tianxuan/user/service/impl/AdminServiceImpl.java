package com.wuyiccc.tianxuan.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.MD5Utils;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.bo.CreateAdminBO;
import com.wuyiccc.tianxuan.user.mapper.AdminMapper;
import com.wuyiccc.tianxuan.user.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/8/22 21:55
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createAdmin(CreateAdminBO createAdminBO) {

        LambdaQueryWrapper<Admin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Admin::getUsername, createAdminBO.getUsername());

        Admin admin = adminMapper.selectOne(wrapper);
        if (Objects.nonNull(admin)) {
            throw new CustomException(ResponseStatusEnum.ADMIN_USERNAME_EXIST_ERROR);
        }

        Admin newAdmin = new Admin();
        BeanUtils.copyProperties(createAdminBO, newAdmin);

        String slat = (int) ((Math.random() * 9 + 1) * 100000) + "";
        String pwd = MD5Utils.encrypt(createAdminBO.getPassword(), slat);
        newAdmin.setSlat(slat);
        newAdmin.setPassword(pwd);
        newAdmin.setCreateTime(LocalDateTime.now());
        newAdmin.setUpdatedTime(LocalDateTime.now());
        adminMapper.insert(newAdmin);
    }

    @Override
    public PagedGridResult getAdminList(String accountName, Integer page, Integer limit) {

        PageHelper.startPage(page, limit);

        LambdaQueryWrapper<Admin> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Admin::getUsername, accountName);

        List<Admin> dataList = adminMapper.selectList(wrapper);

        return PagedGridResult.build(dataList, page);
    }


}
