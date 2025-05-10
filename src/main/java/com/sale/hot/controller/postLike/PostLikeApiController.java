package com.sale.hot.controller.postLike;

import com.sale.hot.controller.post.input.PostsInput;
import com.sale.hot.controller.postLike.input.PostLikesInput;
import com.sale.hot.domain.postLike.service.PostLikeService;
import com.sale.hot.domain.postLike.service.dto.response.PostLikeResponse;
import com.sale.hot.domain.user.service.dto.request.LoginRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;
import com.sale.hot.global.page.Pageable;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post Like API Controller", description = "관심 게시글 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class PostLikeApiController {

    private final PostLikeService postLikeService;

    @Operation(summary = "관심 게시글 조회 API",
            description = "관심 게시글을 조회합니다.")
    @GetMapping("/api/v1/user/post-like")
    public ResponseEntity<Page<List<PostLikeResponse>>> getPostLike(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @ParameterObject @ModelAttribute PostLikesInput input,
            @Parameter(hidden = true) User user
    ){
        PageInput pageInput = PageInput.builder().page(page).size(size).build();
        Page<List<PostLikeResponse>> postLists = postLikeService.getPostLikes(pageInput, input, user);
        return ResponseEntity.ok(postLists);
    }


}
