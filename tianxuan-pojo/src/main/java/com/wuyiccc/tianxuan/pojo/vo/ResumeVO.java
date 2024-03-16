package com.wuyiccc.tianxuan.pojo.vo;

import com.wuyiccc.tianxuan.pojo.ResumeEducation;
import com.wuyiccc.tianxuan.pojo.ResumeProjectExp;
import com.wuyiccc.tianxuan.pojo.ResumeWorkExp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/3/16 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeVO {

    private String id;

    private String userId;

    private String advantage;

    private String advantageHtml;

    private String credentials;

    private String skills;

    private String status;

    private LocalDateTime refreshTime;


    private List<ResumeWorkExp> workExpList;

    private List<ResumeProjectExp> projectExpList;

    private List<ResumeEducation> educationList;
}
