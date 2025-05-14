package com.sale.hot.domain.report.repository;

import com.sale.hot.entity.common.constant.ReportType;
import com.sale.hot.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByTypeAndTargetIdAndUserId(ReportType reportType, Long targetId, Long userId);
}
