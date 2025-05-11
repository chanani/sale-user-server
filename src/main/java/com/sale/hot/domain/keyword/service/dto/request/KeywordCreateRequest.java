package com.sale.hot.domain.keyword.service.dto.request;

import com.sale.hot.entity.keyword.Keyword;
import com.sale.hot.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record KeywordCreateRequest(
        @Schema(description = "등록할 키워드", example = "만두")
        @NotBlank(message = "등록할 키워드는 필수입니다.")
        String keyword
) {

    public Keyword toEntity(User user) {
        return Keyword.builder()
                .name(this.keyword)
                .user(user)
                .build();
    }
}
