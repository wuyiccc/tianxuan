package com.wuyiccc.tianxuan.search.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author wuyiccc
 * @date 2023/9/2 17:55
 */
@Data
@ToString
@NoArgsConstructor
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    private String name;

    private String password;

    private String email;

    private String phoneNumber;

    private int status;

    private Date createTime;

    private Date lastLoginTime;

    private Date lastUpdateTime;

    private String avatar;
}
