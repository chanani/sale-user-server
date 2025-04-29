package com.sale.hot.controller.postLike;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post Like API Controller", description = "관심 게시글 관련 API를 제공합니다.")
@RestController(value = "/api/v1")
@RequiredArgsConstructor
public class PostLikeApiController {
}
