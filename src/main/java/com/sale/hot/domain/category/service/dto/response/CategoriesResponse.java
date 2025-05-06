package com.sale.hot.domain.category.service.dto.response;

import com.sale.hot.entity.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CategoriesResponse {
    @Schema(description = "식별자")
    private Long id;

    @Schema(description = "카테고리명")
    private String name;

    @Schema(description = "카테고리 순서")
    private Integer order;

    public CategoriesResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.order = category.getOrder();
    }
}
