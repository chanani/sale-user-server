package com.sale.hot.domain.comment.service.dto.request;

import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(
        @Schema(description = "상위 댓글 번호")
        Long parentId,

        @Schema(description = "내용")
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {

    public Comment toEntity(Post post, User user, Comment parent){
        return Comment.builder()
                .post(post)
                .user(user)
                .parent(parent)
                .content(this.content)
                .build();
    }

}
