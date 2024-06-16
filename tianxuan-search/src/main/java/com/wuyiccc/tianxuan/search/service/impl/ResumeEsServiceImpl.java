package com.wuyiccc.tianxuan.search.service.impl;

import com.wuyiccc.tianxuan.search.mapper.es.ResumeEsMapper;
import com.wuyiccc.tianxuan.search.pojo.es.ResumeEsEntity;
import com.wuyiccc.tianxuan.search.service.ResumeEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author wuyiccc
 * @date 2024/6/16 11:08
 */
@Slf4j
@Service
public class ResumeEsServiceImpl implements ResumeEsService {

    @Resource
    private ResumeEsMapper resumeEsMapper;


    @Override
    public void insert() {

        ResumeEsEntity resumeEsEntity = generateTestData();
        resumeEsMapper.insert(resumeEsEntity);
    }


    public static ResumeEsEntity generateTestData() {
        ResumeEsEntity resume = new ResumeEsEntity();

        resume.setId(UUID.randomUUID().toString());
        resume.setUserId(UUID.randomUUID().toString());
        resume.setResumeId(UUID.randomUUID().toString());
        resume.setNickname("John Doe");
        resume.setSex(1); // Assuming 1 for male, 2 for female
        resume.setBirthday(LocalDate.of(1990, 1, 1));
        resume.setAge(34);
        resume.setCompanyName("Tech Company");
        resume.setPosition("Software Engineer");
        resume.setIndustry("Information Technology");
        resume.setSchool("XYZ University");
        resume.setEducation("Bachelor's Degree");
        resume.setMajor("Computer Science");
        resume.setResumeExpectId(UUID.randomUUID().toString());
        resume.setWorkYears(10);
        resume.setJobType("Full-Time");
        resume.setCity("New York");
        resume.setBeginSalary(50000);
        resume.setEndSalary(100000);
        resume.setSkills("Java, Spring, Hibernate, SQL");
        resume.setAdvantage("Strong problem-solving skills");
        resume.setAdvantageHtml("<p>Strong problem-solving skills</p>");
        resume.setCredentials("Certified Java Developer");
        resume.setJobStatus("Actively Looking");
        resume.setRefreshTime(LocalDateTime.now());
        resume.setHrCollectResumeTime(LocalDateTime.now().minusDays(1));
        resume.setHrReadResumeTime(LocalDateTime.now().minusDays(2));

        return resume;
    }
}
