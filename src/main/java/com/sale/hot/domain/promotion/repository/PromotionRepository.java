package com.sale.hot.domain.promotion.repository;

import com.sale.hot.entity.promotion.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
