package com.sale.hot.controller.notice;

import com.sale.hot.controller.notice.input.NoticesInput;
import com.sale.hot.domain.notice.service.NoticeService;
import com.sale.hot.domain.notice.service.dto.response.NoticeResponse;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;
import com.sale.hot.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notice API Controller", description = "공지사항 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class NoticeApiController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 목록 API",description = "공지사항 목록을 조회합니다.(페이징된 값 반환)")
    @NoneAuth
    @GetMapping("/api/v1/none/notices")
    public ResponseEntity<Page<List<NoticeResponse>>> getNotices(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @ParameterObject @ModelAttribute NoticesInput input
    ) {
        PageInput pageInput = PageInput.builder().page(page).size(size).build();
        Page<List<NoticeResponse>> notices = noticeService.getNotices(input, pageInput);
        return ResponseEntity.ok(notices);
    }

    // Todo 공지사항 상세 조회 API

    @Operation(summary = "공지사항 조회수 증가 API",description = "공지사항 조회수를 증가합니다.")
    @NoneAuth
    @PutMapping("/api/v1/none/notice/{noticeId}")
    public ResponseEntity<ApiResponse> noticeViewCount(@PathVariable(value = "noticeId") Long noticeId) {
        noticeService.plusNoticeViewCount(noticeId);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
