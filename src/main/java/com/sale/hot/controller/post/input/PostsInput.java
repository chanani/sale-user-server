package com.sale.hot.controller.post.input;

import com.sale.hot.domain.post.repository.condition.PostCondition;
import io.swagger.v3.oas.annotations.media.Schema;

public record PostsInput(
        @Schema(description = "키워드")
        String keyword,

        @Schema(description="검색 유형")
        String type,

        @Schema(description = "카테고리 고유번호")
        Long categoryId
) {
        public PostCondition toCondition(){
                return new PostCondition(this.keyword, this.type, this.categoryId);
        }
}
