package com.sale.hot.controller.category;

import com.sale.hot.domain.category.service.CategoryService;
import com.sale.hot.domain.category.service.dto.response.CategoriesResponse;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Category API Controller", description = "게시글 카테고리 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록 API",description = "카테고리 목록을 조회합니다.")
    @NoneAuth
    @GetMapping("/api/v1/none/categories")
    public ResponseEntity<DataResponse<List<CategoriesResponse>>> getCategories() {
        List<CategoriesResponse> categories = categoryService.getCategories();
        return ResponseEntity.ok(DataResponse.send(categories));
    }
}
