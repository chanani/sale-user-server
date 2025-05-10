package com.sale.hot.domain.postLike.repository.condition;

import com.sale.hot.entity.user.User;

public record PostLikeCondition(
        // 검색어
        String keyword,
        // 유형
        String type,
        // 사용자 정보
        User user
) {

}
