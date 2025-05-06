package com.sale.hot.domain.category.service;

import com.sale.hot.domain.category.service.dto.response.CategoriesResponse;
import com.sale.hot.entity.category.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 카테고리 목록 조회
     * @return 카테고리 리스트
     */
    List<CategoriesResponse> getCategories();
}
