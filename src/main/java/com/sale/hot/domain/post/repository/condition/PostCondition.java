package com.sale.hot.domain.post.repository.condition;

public record PostCondition(
        // 검색어
        String keyword,
        // 유형
        String type,
        // 카테고리 번호
        Long categoryId
) {

}
