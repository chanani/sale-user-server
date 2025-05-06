package com.sale.hot.domain.grade.service.dto.response;

import com.sale.hot.entity.grade.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GradeResponse {
    @Schema(description = "등급 고유번호")
    private Long id;

    @Schema(description = "등급명")
    private String name;

    @Schema(description = "순위")
    private Integer ranking;

    @Schema(description = "출석 횟수 조건")
    private Integer attendanceCount;

    @Schema(description = "게시글 작성 횟수 조건")
    private Integer postCount;

    @Schema(description = "댓글 작성 횟수 조건")
    private Integer commentCount;

    public GradeResponse(Grade grade) {
        this.id = grade.getId();
        this.name = grade.getName();
        this.ranking = grade.getRanking();
        this.attendanceCount = grade.getAttendance();
        this.postCount = grade.getPost();
        this.commentCount = grade.getComment();
    }
}
