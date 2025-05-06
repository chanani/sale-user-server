package com.sale.hot.controller.post;

import com.sale.hot.domain.post.service.PostService;
import com.sale.hot.domain.post.service.dto.request.PostCreateRequest;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post API Controller", description = "게시글 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @Operation(summary = "게시글 등록 API",
            description = """
                    게시글을 등록합니다.
                    제목, 내용은 필수입니다.
                    """)
    @PostMapping("/api/v1/user/post")
    public ResponseEntity<ApiResponse> addNotice(
            @Valid @RequestBody PostCreateRequest request,
            @Parameter(hidden = true) User user
    ) {
        postService.addPost(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
