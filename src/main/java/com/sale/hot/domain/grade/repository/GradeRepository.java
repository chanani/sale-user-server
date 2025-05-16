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

    boolean existsByNameAndStatusAndIdNot(String name, StatusType statusType, Long gradeId);
    
    /**
     * 특정 순위 다음 등급 조회 (현재 등급보다 한 단계 높은 등급)
     */
    Optional<Grade> findByRankingAndStatus(Integer ranking, String status);
    
    /**
     * 특정 순위보다 높은 등급 중 가장 낮은 등급 조회 (다음 등급)
     */
    Optional<Grade> findFirstByRankingGreaterThanAndStatusOrderByRankingAsc(Integer ranking, StatusType status);
}
