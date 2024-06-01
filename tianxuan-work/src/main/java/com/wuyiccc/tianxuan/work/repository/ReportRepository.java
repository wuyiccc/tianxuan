package com.wuyiccc.tianxuan.work.repository;

import com.wuyiccc.tianxuan.pojo.mo.ReportMO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuyiccc
 * @date 2024/6/1 12:10
 */
@Repository
public interface ReportRepository extends MongoRepository<ReportMO, String> {

    public ReportMO findByReportUserIdAndJobId(String reportUserId, String jobId);
}
