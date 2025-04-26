package com.sale.hot.domain.report.repository;

import com.sale.hot.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
