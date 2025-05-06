package com.sale.hot.domain.grade.service.dto.request;

import com.sale.hot.entity.grade.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GradeCreateRequest(
        @Schema(description = "등급명")
        @NotBlank(message = "등급명은 필수입니다.")
        String name,

        @Schema(description = "출석 횟수 조건")
        @NotNull(message = "출석 횟수 조건은 필수입니다.")
        Integer attendance,

        @Schema(description = "게시글 작성 횟수 조건")
        @NotNull(message = "게시글 작성 횟수 조건은 필수입니다.")
        Integer post,

        @Schema(description = "댓글 작성 횟수 조건")
        @NotNull(message = "댓글 작성 횟수 조건은 필수입니다.")
        Integer comment
) {

    public Grade toEntity(Integer ranking){
        return Grade.builder()
                .name(this.name)
                .attendance(this.attendance)
                .post(this.post)
                .comment(this.comment)
                .ranking(ranking)
                .build();
    }
}
