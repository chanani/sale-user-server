package com.sale.hot.domain.category.repository;

import com.sale.hot.entity.category.Category;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByStatusAndActiveOrderByOrder(StatusType statusType, BooleanYn active);
}
