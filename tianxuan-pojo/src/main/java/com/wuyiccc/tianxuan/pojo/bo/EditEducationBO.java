package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2024/5/21 23:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditEducationBO {

    private String id;

    private String userId;

    private String resumeId;

    private String school;

    private String education;

    private String major;

    private String beginDate;

    private String endDate;

}
