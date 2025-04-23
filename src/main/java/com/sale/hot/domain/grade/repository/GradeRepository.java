package com.sale.hot.domain.grade.repository;

import com.sale.hot.entity.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
