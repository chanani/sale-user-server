package com.sale.hot.controller.post;

import com.sale.hot.controller.post.input.PostsInput;
import com.sale.hot.domain.grade.service.dto.response.GradeUpdateResponse;
import com.sale.hot.domain.post.service.PostService;
import com.sale.hot.domain.post.service.dto.request.PostCreateRequest;
import com.sale.hot.domain.post.service.dto.request.PostUpdateRequest;
import com.sale.hot.domain.post.service.dto.response.PostResponse;
import com.sale.hot.domain.post.service.dto.response.PostsResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;
import com.sale.hot.global.response.ApiResponse;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Post API Controller", description = "게시글 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/json")
public class PostApiController {

    private final PostService postService;

    @Operation(summary = "게시글 목록 조회 API", description = """
            게시글을 조회합니다.
            keyword : 검색어
            type : 검색 유형 [title, content, comment, nickname]
            """)
    @NoneAuth
    @GetMapping("/api/v1/none/posts")
    public ResponseEntity<Page<List<PostsResponse>>> getPosts(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @ParameterObject @ModelAttribute PostsInput input
    ) {
        PageInput pageInput = PageInput.builder().page(page).size(size).build();
        Page<List<PostsResponse>> posts = postService.getPosts(input, pageInput);
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "게시글 단건 조회 API", description = "게시글 상세내용을 조회합니다.")
    @NoneAuth
    @GetMapping("/api/v1/none/post/{postId}")
    public ResponseEntity<DataResponse<PostResponse>> getPost(
            @PathVariable(name = "postId") Long postId
    ) {
        PostResponse post = postService.getPost(postId);
        return ResponseEntity.ok(DataResponse.send(post));
    }

    @Operation(summary = "게시글 등록 API", description = """
            게시글을 등록합니다. 제목, 내용은 필수입니다.
            기업이 아닌 기본 사용자가 게시글을 등록할 경우 promotion은 꼭 false로 전달해주세요.
            """)
    @PostMapping(value = "/api/v1/user/post", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<DataResponse<GradeUpdateResponse>> addNotice(
            @Valid @RequestPart(value = "postCreateRequest") PostCreateRequest request,
            @RequestPart(value = "file", required = false) MultipartFile thumbnail,
            @Parameter(hidden = true) User user
    ) {
        GradeUpdateResponse gradeUpdate = postService.addPost(request, user, thumbnail);
        return ResponseEntity.ok(DataResponse.send(gradeUpdate));
    }

    @Operation(summary = "게시글 수정 API", description = "게시글을 수정합니다.")
    @PutMapping(value = "/api/v1/user/post/{postId}", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<ApiResponse> updateNotice(
            @PathVariable(name = "postId") Long postId,
            @Valid @RequestPart(value = "postUpdateRequest") PostUpdateRequest request,
            @RequestPart(value = "file", required = false) MultipartFile thumbnail,
            @Parameter(hidden = true) User user
    ) {
        postService.updatePost(postId, request, user, thumbnail);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "게시글 삭제 API", description = "게시글을 삭제합니다.")
    @DeleteMapping("/api/v1/user/post/{postId}")
    public ResponseEntity<ApiResponse> deleteNotice(
            @PathVariable(name = "postId") Long postId,
            @Parameter(hidden = true) User user
    ) {
        postService.deletePost(postId, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "게시글 좋아요 & 싫어요 API", description = """
            게시글 좋아요와 싫어요를 등록 및 삭제합니다.
            type은 like, dislike로 전달 부탁드립니다.
            """)
    @PostMapping("/api/v1/user/posts/{postId}/{type}")
    public ResponseEntity<ApiResponse> addPostLike(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "type") String type,
            @Parameter(hidden = true) User user
    ) {
        postService.toggleLikeAndDisLike(postId, type, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
