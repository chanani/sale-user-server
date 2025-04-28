package com.sale.hot.domain.company.repository;

import com.sale.hot.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
