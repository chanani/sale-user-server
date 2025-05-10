package com.sale.hot.domain.keyword.repository;

import com.sale.hot.entity.keyword.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
