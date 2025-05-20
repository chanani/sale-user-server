package com.sale.hot.domain.comment.service.dto.response;

import com.sale.hot.domain.post.service.dto.response.PostCompanyResponse;
import com.sale.hot.domain.post.service.dto.response.PostUserResponse;
import com.sale.hot.entity.common.constant.AuthorType;
import com.sale.hot.global.util.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponse {

    @Schema(description = "댓글 고유번호")
    private Long id;

    @Schema(description = "작성자 타입")
    private AuthorType authorType;

    @Schema(description = "상위 댓글 고유번호")
    private Long parentId;

    @Schema(description = "작성자 회원 정보")
    private PostUserResponse user;

    @Schema(description = "작성자 기업 정보")
    private PostCompanyResponse company;

    @Schema(description = "댓글 내용")
    private String content;

    @Schema(description = "좋아요")
    private Integer likeCount;

    @Schema(description = "싫어요")
    private Integer dislikeCount;

    @Schema(description = "댓글 등록 시간")
    private LocalDateTime createdAt;

    @Schema(description = "대댓글 목록")
    private List<CommentResponse> reComments;

    public CommentResponse(Long id, PostUserResponse user, PostCompanyResponse company,
                           AuthorType authorType, Long parentId, String content, Integer likeCount,
                           Integer dislikeCount, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.company = company;
        this.authorType = authorType;
        this.parentId = parentId;
        this.content = content;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.createdAt = createdAt;
    }

    /**
     * 대댓글 등록
     */
    public void addRecomment(List<CommentResponse> reComments) {
        this.reComments = reComments;
    }

}
