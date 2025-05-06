package com.sale.hot.domain.category.service;

import com.sale.hot.domain.category.service.dto.request.CategoryCreateRequest;
import com.sale.hot.domain.category.service.dto.request.CategoryUpdateRequest;
import com.sale.hot.domain.category.service.dto.response.CategoriesResponse;
import com.sale.hot.entity.category.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 카테고리 목록 조회
     * @return 카테고리 리스트
     */
    List<CategoriesResponse> getCategories();

    /**
     * 카테고리 등록
     * @param request 카테고리 등록 요청 객체
     */
    void addCategory(CategoryCreateRequest request);

    /**
     * 카테고리 정보 수정
     * @param categoryId 카테고리 식별자
     * @param request 카테고리 수정 요청 객체
     */
    void updateCategory(Long categoryId, CategoryUpdateRequest request);
}
