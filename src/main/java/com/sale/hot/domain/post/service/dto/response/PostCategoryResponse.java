package com.sale.hot.domain.post.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PostCategoryResponse {

    @Schema(description = "카테고리 ID")
    private Long categoryId;

    @Schema(description = "카테고리명")
    private String categoryName;

    public PostCategoryResponse(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
