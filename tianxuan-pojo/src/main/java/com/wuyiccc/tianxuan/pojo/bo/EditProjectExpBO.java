package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2024/5/19 23:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditProjectExpBO {

    private String id;

    private String userId;

    private String resumeId;

    private String projectName;

    private String roleName;

    private String beginDate;

    private String endDate;

    private String content;

    private String contentHtml;
}
