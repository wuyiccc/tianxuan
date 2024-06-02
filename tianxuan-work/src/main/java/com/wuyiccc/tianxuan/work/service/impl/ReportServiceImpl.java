package com.wuyiccc.tianxuan.work.service.impl;

import com.wuyiccc.tianxuan.common.enumeration.DealStatusEnum;
import com.wuyiccc.tianxuan.common.enumeration.JobStatusEnum;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.Job;
import com.wuyiccc.tianxuan.pojo.bo.SearchReportJobBO;
import com.wuyiccc.tianxuan.pojo.mo.ReportMO;
import com.wuyiccc.tianxuan.work.mapper.JobMapper;
import com.wuyiccc.tianxuan.work.repository.ReportRepository;
import com.wuyiccc.tianxuan.work.service.ReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author wuyiccc
 * @date 2024/6/1 12:11
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportRepository reportRepository;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private JobMapper jobMapper;

    @Override
    public boolean isReportRecordExist(String reportUserId, String jobId) {

        ReportMO reportMO = reportRepository.findByReportUserIdAndJobId(reportUserId, jobId);
        return !Objects.isNull(reportMO);
    }

    @Override
    public void saveReportRecord(ReportMO reportMO) {

        reportMO.setDealStatus(DealStatusEnum.WAITING.type);
        reportMO.setCreatedTime(LocalDateTime.now());
        reportMO.setUpdatedTime(LocalDateTime.now());

        reportRepository.save(reportMO);
    }

    @Override
    public PagedGridResult pagedReportRecordList(SearchReportJobBO reportJobBO, Integer page, Integer pageSize) {

        String jobName = reportJobBO.getJobName();
        String companyName = reportJobBO.getCompanyName();
        String reportUserName = reportJobBO.getReportUserName();
        Integer dealStatus = reportJobBO.getDealStatus();
        LocalDateTime beginDate = reportJobBO.getBeginDateTime();
        LocalDateTime endDate = reportJobBO.getEndDateTime();

        // 1. 创建查询对象
        Query query = new Query();

        // 2. 创建条件对象
        //Criteria criteria = new Criteria();

        // 3. 设置查询条件参数
        if (StringUtils.isNotBlank(jobName)) {
            query = addLikeByValue(query, "job_name", jobName);
        }
        if (StringUtils.isNotBlank(companyName)) {
            query = addLikeByValue(query, "company_name", companyName);
        }
        if (StringUtils.isNotBlank(reportUserName)) {
            query = addLikeByValue(query, "report_user_name", reportUserName);
        }

        if (dealStatus != null) {
            query.addCriteria(Criteria.where("deal_status").is(dealStatus));
        }

        if (beginDate != null && endDate == null) {
            query.addCriteria(Criteria.where("created_time").gte(beginDate));
        } else if (beginDate == null && endDate != null) {
            query.addCriteria(Criteria.where("created_time").lte(endDate));
        } else if (beginDate != null) {
            query.addCriteria(Criteria.where("created_time").gte(beginDate).lte(endDate));
        }

        // 4. 查询记录总数，必须在分页前查询，否则总数不对
        long counts = mongoTemplate.count(query, ReportMO.class);

        // 5. 设置分页
        Pageable pageable = PageRequest.of(page,
                pageSize,
                Sort.Direction.DESC,
                "created_time");
        query.with(pageable);

        // 6. 执行查询
        List<ReportMO> list = mongoTemplate.find(query, ReportMO.class);

        // 7. 封装分页grid信息数据
        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setRows(list);
        gridResult.setPage(page);
        gridResult.setRecords(counts);

        return gridResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateReportRecordStatus(String reportId, DealStatusEnum dealStatusEnum) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(reportId));

        Update update = new Update();
        update.set("deal_status", dealStatusEnum.type);
        update.set("updated_time", LocalDateTime.now());


        if (Objects.equals(DealStatusEnum.DONE, dealStatusEnum)) {
            // 修改状态为已处理的时候, 需要同步关闭职位
            ReportMO tmp = reportRepository.findById(reportId).get();

            String jobId = tmp.getJobId();
            Job pending = new Job();
            pending.setId(jobId);
            pending.setStatus(JobStatusEnum.DELETE.code);
            pending.setViolateReason(tmp.getReportReason());
            pending.setUpdatedTime(LocalDateTime.now());


            int res = jobMapper.updateById(pending);
            if (res != 1) {
                throw new CustomException("职位不存在");
            }
        }

        mongoTemplate.updateFirst(query, update, ReportMO.class);
    }


    private Query addLikeByValue(Query query, String key, String value) {
        // 拼接 正则表达式和查询参数
        Pattern pattern = Pattern.compile("^.*" + value + ".*$");
        // 指定要查询的属性
        query.addCriteria(Criteria.where(key).regex(pattern));
        return query;
    }
}
