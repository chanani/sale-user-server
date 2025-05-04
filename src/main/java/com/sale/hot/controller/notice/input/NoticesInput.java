package com.sale.hot.controller.notice.input;

import com.sale.hot.domain.notice.repository.condition.NoticeCondition;
import io.swagger.v3.oas.annotations.media.Schema;

public record NoticesInput(
        @Schema(description = "검색어")
        String keyword
) {
    public NoticeCondition toCondition() {
        return new NoticeCondition(this.keyword);
    }
}
