package com.sale.hot.domain.category.service.dto.request;

import com.sale.hot.entity.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequest(
        @Schema(description = "카테고리명")
        @NotBlank(message = "카테고리명은 필수입니다.")
        String name
) {

        public Category toEntity(){
                return Category.builder()
                        .name(this.name)
                        .build();
        }
}
