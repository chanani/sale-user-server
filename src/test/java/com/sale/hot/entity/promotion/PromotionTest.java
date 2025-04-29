package com.sale.hot.entity.promotion;

import com.sale.hot.domain.promotion.repository.PromotionRepository;
import com.sale.hot.entity.common.constant.PromotionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PromotionTest {

    @Autowired
    PromotionRepository promotionRepository;

    @Test
    public void create() throws Exception {
        Promotion promotion = Promotion.builder()
                .type(PromotionType.POST)
                .amount(100000)
                .build();

        Promotion save = promotionRepository.save(promotion);
        System.out.println("save. = " + save.getType());

    }
}