package com.sale.hot.controller.comment;

import com.sale.hot.domain.comment.service.CommentService;
import com.sale.hot.domain.comment.service.dto.request.CommentCreateRequest;
import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;
import com.sale.hot.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment API Controller", description = "댓글 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @Operation(summary = "특정 게시글의 댓글 목록 조회 API", description = "특정 게시글의 댓글을 조회합니다.")
    @NoneAuth
    @GetMapping("/api/v1/none/comments/{postId}")
    public ResponseEntity<Page<List<CommentResponse>>> getComments(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @PathVariable(name = "postId") Long postId
    ) {
        PageInput pageInput = PageInput.builder().page(page).size(size).build();
        Page<List<CommentResponse>> comments = commentService.getComments(pageInput, postId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 등록 API", description = """
            댓글을 등록합니다.
            상위 댓글이 없을 경우 parentId는 null 또는 0으로 전달해주세요.
            """)
    @PostMapping("/api/v1/user/comments/{postId}")
    public ResponseEntity<ApiResponse> addComment(
            @PathVariable(name = "postId") Long postId,
            @Parameter(hidden = true) User user,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        commentService.addComment(postId, user, request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "댓글 삭제 API", description = "댓글을 삭제합니다.")
    @DeleteMapping("/api/v1/user/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable(name = "commentId") Long commentId,
            @Parameter(hidden = true) User user
    ) {
        commentService.deleteComment(commentId, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "댓글 좋아요 & 싫어요 API", description = """
            댓글 좋아요와 싫어요를 등록 및 삭제합니다.
            type은 like, dislike로 전달 부탁드립니다.
            """)
    @PostMapping("/api/v1/user/comments/{commentId}/{type}")
    public ResponseEntity<ApiResponse> addCommentLike(
            @PathVariable(name = "commentId") Long commentId,
            @PathVariable(name = "type") String type,
            @Parameter(hidden = true) User user
    ) {
        commentService.toggleLikeAndDisLike(commentId, type, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }


}
