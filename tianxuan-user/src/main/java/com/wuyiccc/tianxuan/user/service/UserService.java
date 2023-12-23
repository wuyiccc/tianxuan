package com.wuyiccc.tianxuan.user.service; import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.bo.ModifyUserBO;
import com.wuyiccc.tianxuan.pojo.vo.UserVO;

/**
 * @author wuyiccc
 * @date 2023/12/22 20:54
 */
public interface UserService {

    public void modifyUserInfo(ModifyUserBO modifyUserBO);

    public User getById(String id);
}