package com.wuyiccc.tianxuan.search.pojo.es;


import lombok.Data;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.Analyzer;
import org.dromara.easyes.annotation.rely.FieldType;
import org.dromara.easyes.annotation.rely.IdType;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/16 10:13
 */
@Data
@IndexName(value = "resume", aliasName = "resume_alias")
public class ResumeEsEntity {


    @IndexId(type = IdType.CUSTOMIZE)
    private String id;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String userId;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String resumeId;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String nickname;

    @IndexField(fieldType = FieldType.INTEGER)
    private Integer sex;

    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd")
    private LocalDate birthday;

    @IndexField(fieldType = FieldType.INTEGER)
    private Integer age;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String companyName;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String position;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String industry;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String school;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String education;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String major;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String resumeExpectId;

    @IndexField(fieldType = FieldType.INTEGER)
    private Integer workYears;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String jobType;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String city;

    @IndexField(fieldType = FieldType.INTEGER)
    private Integer beginSalary;

    @IndexField(fieldType = FieldType.INTEGER)
    private Integer endSalary;


    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String skills;


    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String advantage;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String advantageHtml;


    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String credentials;


    @IndexField(fieldType = FieldType.KEYWORD)
    private String jobStatus;

    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refreshTime;


    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrCollectResumeTime;

    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hrReadResumeTime;

}
