package com.wuyiccc.tianxuan.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.bo.AdminLoginBO;
import com.wuyiccc.tianxuan.pojo.vo.AdminUserLoginVO;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/8/20 11:26
 */
public interface AdminService extends IService<Admin> {

    public AdminUserLoginVO login(AdminLoginBO adminLoginBO);

    public void createAdmin(Admin admin);

    List<Admin> findAll(Admin admin);

    void updateAdmin(Admin admin);
}
