package com.sale.hot.domain.category.service;

import com.sale.hot.domain.category.repository.CategoryRepository;
import com.sale.hot.domain.category.service.dto.response.CategoriesResponse;
import com.sale.hot.entity.common.constant.StatusType;
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

}
