package com.sale.hot.domain.grade.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GradeUpdateResponse {

    @Schema(description = "등업 여부")
    private boolean isUpdated;

    @Schema(description = "등업된 등급명")
    private String updatedGradeName;

    public GradeUpdateResponse(boolean isUpdated, String updatedGradeName) {
        this.isUpdated = isUpdated;
        this.updatedGradeName = updatedGradeName;
    }
}
