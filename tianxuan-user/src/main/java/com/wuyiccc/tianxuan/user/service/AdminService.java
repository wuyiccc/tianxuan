package com.wuyiccc.tianxuan.user.service;

import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.bo.CreateAdminBO;
import com.wuyiccc.tianxuan.pojo.bo.ResetPwdBO;

/**
 * @author wuyiccc
 * @date 2023/8/22 21:54
 */
public interface AdminService {

    void createAdmin(CreateAdminBO createAdminBO);

    PagedGridResult getAdminList(String accountName, Integer page, Integer limit);

    void delete(String username);

    void resetPassword(ResetPwdBO resetPwdBO);
}
