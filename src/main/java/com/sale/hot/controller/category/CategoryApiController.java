package com.sale.hot.controller.category;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category API Controller", description = "게시글 카테고리 관련 API를 제공합니다.")
@RestController(value = "/api/v1")
@RequiredArgsConstructor
public class CategoryApiController {

}
