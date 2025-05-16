package com.sale.hot.domain.user.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    @Schema(description = "accessToken")
    private String accessToken;

    @Schema(description = "refreshToken")
    private String refreshToken;

    @Schema(description = "등업 여부 : null이 아닐 경우 등급명 노출")
    private String gradeName;
}
