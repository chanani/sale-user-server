package com.sale.hot.domain.grade.repository;

import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findFirstByStatusOrderByRankingAsc(StatusType statusType);
    List<Grade> findByStatusOrderByRankingAsc(StatusType statusType);
    boolean existsByNameAndStatus(String name, StatusType statusType);

    @Query("SELECT MAX(g.ranking) FROM Grade g WHERE g.status = :statusType")
    Optional<Integer> findMaxRanking(StatusType statusType);
}
