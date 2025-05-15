package com.sale.hot.domain.company.repository;

import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByCompanyId(String companyId);
    boolean existsByPhoneAndStatus(String phone, StatusType statusType);
    boolean existsByPhoneAndStatusAndIdNot(String phone, StatusType statusType, Long userId);
    boolean existsByEmail(String email);
}
