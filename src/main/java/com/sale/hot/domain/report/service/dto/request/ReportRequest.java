package com.sale.hot.domain.report.service.dto.request;

import com.sale.hot.entity.common.constant.ReportType;
import com.sale.hot.entity.report.Report;
import com.sale.hot.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReportRequest(
        @Schema(description = "신고 타입")
        @NotBlank(message = "신고 타입은 필수입니다.")
        String type,

        @Schema(description = "타겟 ID(게시글일 경우 postId, 회원일 경우 userId, 댓글일 경우 commentId)")
        @NotNull(message = "타겟 ID는 필수입니다.")
        Long targetId,

        @Schema(description = "신고 사유")
        @NotBlank(message = "신고 사유는 필수입니다.")
        String reason
) {

    public Report toEntity(User user) {
        return Report.builder()
                .type(ReportType.forValue(this.type))
                .targetId(this.targetId)
                .reason(this.reason)
                .user(user)
                .build();
    }
}
