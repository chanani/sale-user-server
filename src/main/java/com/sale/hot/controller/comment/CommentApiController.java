package com.sale.hot.controller.comment;

import com.sale.hot.domain.comment.service.CommentService;
import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        Page<List<CommentResponse>> comments = commentService.getComments(pageInput ,postId);
        return ResponseEntity.ok(comments);
    }
}
