package com.sale.hot.domain.category.service;

import com.sale.hot.domain.category.repository.CategoryRepository;
import com.sale.hot.domain.category.service.dto.request.CategoryCreateRequest;
import com.sale.hot.domain.category.service.dto.request.CategoryUpdateRequest;
import com.sale.hot.domain.category.service.dto.response.CategoriesResponse;
import com.sale.hot.entity.category.Category;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoriesResponse> getCategories() {
        return categoryRepository.findAllByStatusOrderByOrder(StatusType.ACTIVE).stream()
                .map(CategoriesResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public void addCategory(CategoryCreateRequest request) {
        // 카테고리명 중복 검사
        if (categoryRepository.existsByName(request.name())) {
            throw new OperationErrorException(ErrorCode.EXISTS_CATEGORY_NAME);
        }

        // 순서 조회(제일 마지막 순번으로 등록)
        Integer order = categoryRepository.findMaxSortOrder(StatusType.ACTIVE).orElse(0);

        // 카테고리 등록
        Category newCategory = request.toEntity(++order);
        categoryRepository.save(newCategory);
    }

    @Override
    @Transactional
    public void updateCategory(Long categoryId, CategoryUpdateRequest request) {
        Category findCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_CATEGORY));

        // 카테고리 수정
        Category newCategory = request.toEntity();
        findCategory.update(newCategory);
    }
}
