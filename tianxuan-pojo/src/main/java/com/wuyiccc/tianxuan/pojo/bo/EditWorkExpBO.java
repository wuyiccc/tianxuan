package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2024/3/16 20:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditWorkExpBO {

    private String id;

    private String userId;

    private String resumeId;

    private String companyName;

    private String industry;

    private String beginDate;

    private String endDate;

    private String position;

    private String department;

    private String content;

    private String contentHtml;

}
