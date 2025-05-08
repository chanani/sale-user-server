package com.sale.hot.entity.category;

import com.sale.hot.domain.category.repository.CategoryRepository;
import com.sale.hot.entity.common.constant.BooleanYn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryKindTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void createTest(){
        Category category = Category.builder()
                .name("먹거리")
                .order(1)
                .build();
        Category result = categoryRepository.save(category);
        System.out.println("result = " + result.getName());
    }

}