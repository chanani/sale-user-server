package com.sale.hot.controller.post;

import com.sale.hot.controller.post.input.PostsInput;
import com.sale.hot.domain.post.service.PostService;
import com.sale.hot.domain.post.service.dto.request.PostCreateRequest;
import com.sale.hot.domain.post.service.dto.request.PostUpdateRequest;
import com.sale.hot.domain.post.service.dto.response.PostsResponse;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post API Controller", description = "게시글 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @Operation(summary = "게시글 목록 조회 API", description = """
            게시글을 조회합니다.
            keyword : 검색어
            type : 검색 유형 [title, content, comment, nickname]
            """)
    @NoneAuth
    @GetMapping("/api/v1/none/posts")
    public ResponseEntity<Page<List<PostsResponse>>> getNotices(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @ParameterObject @ModelAttribute PostsInput input
    ) {
        PageInput pageInput = PageInput.builder().page(page).size(size).build();
        Page<List<PostsResponse>> posts = postService.getPosts(input, pageInput);
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "게시글 등록 API", description = """
            게시글을 등록합니다. 제목, 내용은 필수입니다.
            기업이 아닌 기본 사용자가 게시글을 등록할 경우 promotion은 꼭 false로 전달해주세요.
            """)
    @PostMapping("/api/v1/user/post")
    public ResponseEntity<ApiResponse> addNotice(
            @Valid @RequestBody PostCreateRequest request, @Parameter(hidden = true) User user
    ) {
        postService.addPost(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "게시글 수정 API", description = "게시글을 수정합니다.")
    @PutMapping("/api/v1/user/post/{postId}")
    public ResponseEntity<ApiResponse> updateNotice(
            @PathVariable(name = "postId") Long postId, @Valid @RequestBody PostUpdateRequest request
    ) {
        postService.updatePost(postId, request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "게시글 삭제 API", description = "게시글을 삭제합니다.")
    @DeleteMapping("/api/v1/user/post/{postId}")
    public ResponseEntity<ApiResponse> deleteNotice(@PathVariable(name = "postId") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
