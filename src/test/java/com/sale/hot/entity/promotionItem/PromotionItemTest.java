package com.sale.hot.entity.promotionItem;

import com.sale.hot.domain.promotionItem.repository.PromotionItemRepository;
import com.sale.hot.entity.common.constant.PromotionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PromotionItemTest {

    @Autowired
    PromotionItemRepository promotionRepository;

    @Test
    public void create() throws Exception {
        PromotionItem promotion = PromotionItem.builder()
                .type(PromotionType.POST)
                .amount(100000)
                .build();

        PromotionItem save = promotionRepository.save(promotion);
        System.out.println("save. = " + save.getType());

    }
}