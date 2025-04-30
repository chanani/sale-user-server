package com.sale.hot.controller.post;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post API Controller", description = "게시글 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class PostApiController {
}
