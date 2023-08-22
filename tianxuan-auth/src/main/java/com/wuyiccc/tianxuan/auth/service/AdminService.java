package com.wuyiccc.tianxuan.auth.service;

import com.wuyiccc.tianxuan.pojo.Admin;
import com.wuyiccc.tianxuan.pojo.bo.AdminBO;

/**
 * @author wuyiccc
 * @date 2023/8/22 21:54
 */
public interface AdminService {

    public Admin adminLogin(AdminBO adminBO);
}
