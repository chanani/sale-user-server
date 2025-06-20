package com.sale.hot.infra.naver.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record NaverMergeRequestDto(
        @Schema(description = "인가 코드")
        @NotNull(message = "인가 코드는 필수입니다.")
        String code
) {
}
