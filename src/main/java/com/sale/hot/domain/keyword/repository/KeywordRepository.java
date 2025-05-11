package com.sale.hot.domain.keyword.repository;

import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.keyword.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    boolean existsByUserIdAndName(Long id, String keyword);
    Optional<Keyword> findByIdAndUserIdAndStatus(Long keywordId, Long id, StatusType statusType);
    List<Keyword> findByUserIdAndStatusOrderByCreatedAtDesc(Long id, StatusType statusType);
    Long countByUserIdAndStatus(Long id, StatusType statusType);
}
