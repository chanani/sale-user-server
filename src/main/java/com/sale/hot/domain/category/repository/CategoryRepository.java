package com.sale.hot.domain.category.repository;

import com.sale.hot.entity.category.Category;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByStatusOrderByOrder(StatusType statusType);

    boolean existsByName(String name);

    @Query("SELECT MAX(c.order) FROM Category c WHERE c.status = :statusType")
    Optional<Integer> findMaxSortOrder(StatusType statusType);

    @Modifying
    @Query("UPDATE Category c set c.order = c.order + 1 WHERE c.order BETWEEN :changeOrder AND :currentOrder")
    int plusOrderAll(int changeOrder, int currentOrder);

    @Modifying
    @Query("UPDATE Category c set c.order = c.order - 1 WHERE c.order > :currentOrder AND c.order <= :changeOrder ")
    int minusOrderAll(int changeOrder, int currentOrder);
}
