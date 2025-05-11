package com.sale.hot.controller.keyword;

import com.sale.hot.domain.keyword.service.KeywordService;
import com.sale.hot.domain.keyword.service.dto.request.KeywordCreateRequest;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Keyword API Controller", description = "키워드 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class KeywordApiController {

    private final KeywordService keywordService;

    @Operation(summary = "알림 받을 키워드 등록 API",
            description = "알림 받을 키워드를 등록합니다.")
    @PostMapping("/api/v1/user/keyword")
    public ResponseEntity<ApiResponse> addKeyword(
            @Valid @RequestBody KeywordCreateRequest request,
            @Parameter(hidden = true) User user
    ){
        keywordService.addKeyword(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
