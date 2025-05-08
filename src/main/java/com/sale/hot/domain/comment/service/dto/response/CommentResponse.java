package com.sale.hot.domain.comment.service.dto.response;

import com.sale.hot.domain.post.service.dto.response.PostUserResponse;
import com.sale.hot.global.util.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponse {

    @Schema(description = "댓글 고유번호")
    private Long id;

    @Schema(description = "작성자 정보")
    private PostUserResponse user;

    @Schema(description = "댓글 내용")
    private String content;

    @Schema(description = "좋아요")
    private Integer likeCount;

    @Schema(description = "싫어요")
    private Integer dislikeCount;

    @Schema(description = "댓글 등록 시간")
    private String createdAt;

    @Schema(description = "대댓글 목록")
    private List<CommentResponse> reComments;

    public CommentResponse(Long id, PostUserResponse user,
                           String content, Integer likeCount,
                           Integer dislikeCount, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.createdAt = DateUtil.localDateTimeTolocalDateTimeString(createdAt);
    }

}
