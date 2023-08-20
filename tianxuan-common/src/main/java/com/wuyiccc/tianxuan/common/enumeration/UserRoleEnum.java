package com.wuyiccc.tianxuan.common.enumeration;

/**
 * @author wuyiccc
 * @date 2023/6/27 23:29
 */
public enum UserRoleEnum {

    CANDIDATE(1, "求职者"),
    RECRUITER(2, "求职者、招聘者");
    ;

    public final Integer code;

    public final String desc;

    UserRoleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
