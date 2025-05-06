package com.sale.hot.domain.category.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateOrderRequest(
        @Schema(description = "별경할 순서")
        @NotNull(message = "변경할 순서는 필수입니다.")
        @Min(value = 0, message = "0보다 작을 수 없습니다.")
        Integer order
) {

}
