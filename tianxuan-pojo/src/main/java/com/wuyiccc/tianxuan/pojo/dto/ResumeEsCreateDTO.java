package com.wuyiccc.tianxuan.pojo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/16 14:51
 */
@Data
public class ResumeEsCreateDTO {


    private String id;

    private String userId;

    private String resumeId;

    private String nickname;

    private Integer sex;

    private LocalDate birthday;

    private Integer age;

    private String companyName;

    private String position;

    private String industry;

    private String school;

    private String education;

    private String major;

    private String resumeExpectId;

    private Integer workYears;

    private String jobType;

    private String city;

    private Integer beginSalary;

    private Integer endSalary;


    private String skills;


    private String advantage;

    private String advantageHtml;


    private String credentials;


    private String jobStatus;

    private LocalDateTime refreshTime;


    private LocalDateTime hrCollectResumeTime;

    private LocalDateTime hrReadResumeTime;
}
