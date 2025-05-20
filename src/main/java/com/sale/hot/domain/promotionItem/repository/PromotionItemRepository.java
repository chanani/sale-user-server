package com.sale.hot.domain.promotionItem.repository;

import com.sale.hot.entity.promotionItem.PromotionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionItemRepository extends JpaRepository<PromotionItem, Long> {
}
