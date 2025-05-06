package com.sale.hot.domain.grade.repository;

import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findFirstByStatusOrderByRankingAsc(StatusType statusType);
    List<Grade> findByStatusOrderByRankingAsc(StatusType statusType);
}
