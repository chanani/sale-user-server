package com.sale.hot.domain.category.repository;

import com.sale.hot.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
