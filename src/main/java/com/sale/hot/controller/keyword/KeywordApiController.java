package com.sale.hot.controller.keyword;

import com.sale.hot.domain.keyword.service.KeywordService;
import com.sale.hot.domain.keyword.service.dto.request.KeywordCreateRequest;
import com.sale.hot.domain.keyword.service.dto.response.KeywordResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.response.ApiResponse;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Keyword API Controller", description = "키워드 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class KeywordApiController {

    private final KeywordService keywordService;

    @Operation(summary = "알림 받을 키워드 등록 API",
            description = """
                    알림 받을 키워드를 등록합니다.
                    키워드 알림 등록은 사용자당 30개까지만 등록 가능합니다.
                    """)
    @PostMapping("/api/v1/user/keywords")
    public ResponseEntity<ApiResponse> addKeyword(
            @Valid @RequestBody KeywordCreateRequest request,
            @Parameter(hidden = true) User user
    ) {
        keywordService.addKeyword(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "알림 받을 키워드 삭제 API",
            description = "알림 받을 키워드를 삭제합니다.")
    @DeleteMapping("/api/v1/user/keywords/{keywordId}")
    public ResponseEntity<ApiResponse> deleteKeyword(
            @PathVariable(name = "keywordId") Long keywordId,
            @Parameter(hidden = true) User user
    ) {
        keywordService.deleteKeyword(keywordId, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "알림 받을 키워드 목록 조회 API",
            description = "알림 받을 키워드를 조회합니다.")
    @GetMapping("/api/v1/user/keywords")
    public ResponseEntity<DataResponse<List<KeywordResponse>>> getKeywords(
            @Parameter(hidden = true) User user
    ) {
        List<KeywordResponse> keywords = keywordService.getKeywords(user);
        return ResponseEntity.ok(DataResponse.send(keywords));
    }

}
