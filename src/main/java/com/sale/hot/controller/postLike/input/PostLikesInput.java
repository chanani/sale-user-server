package com.sale.hot.controller.postLike.input;

import com.sale.hot.domain.postLike.repository.condition.PostLikeCondition;
import com.sale.hot.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record PostLikesInput(
        @Schema(description = "키워드")
        String keyword,

        @Schema(description="검색 유형")
        String type
) {

    public PostLikeCondition toCondition(User user) {
        return new PostLikeCondition(this.keyword, this.type,  user);
    }

}
