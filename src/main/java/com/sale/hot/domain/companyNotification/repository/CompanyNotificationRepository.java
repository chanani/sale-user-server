package com.sale.hot.domain.companyNotification.repository;

import com.sale.hot.entity.companyNotification.CompanyNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyNotificationRepository extends JpaRepository<CompanyNotification, Long> {
}
