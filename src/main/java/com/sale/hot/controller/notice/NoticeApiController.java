package com.sale.hot.controller.notice;

import com.sale.hot.controller.notice.input.NoticesInput;
import com.sale.hot.domain.notice.service.NoticeService;
import com.sale.hot.domain.notice.service.dto.request.NoticeCreateRequest;
import com.sale.hot.domain.notice.service.dto.request.NoticeUpdateRequest;
import com.sale.hot.domain.notice.service.dto.response.NoticeResponse;
import com.sale.hot.entity.notice.Notice;
import com.sale.hot.entity.operator.Operator;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notice API Controller", description = "공지사항 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class NoticeApiController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 목록 API")
    @NoneAuth
    @GetMapping("/api/v1/none/notices")
    public ResponseEntity<Page<List<NoticeResponse>>> notices(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @ParameterObject @ModelAttribute NoticesInput input
    ) {
        PageInput pageInput = PageInput.builder().page(page).size(size).build();
        Page<List<NoticeResponse>> notices = noticeService.getNotices(input, pageInput);
        return ResponseEntity.ok(notices);
    }

    @Operation(summary = "공지사항 조회수 증가 API")
    @NoneAuth
    @PutMapping("/api/v1/none/notice/{id}")
    public ResponseEntity<ApiResponse> noticeViewCount(@PathVariable(value = "id") Long id) {
        noticeService.plusNoticeViewCount(id);
        return ResponseEntity.ok(ApiResponse.ok());
    }


    @Operation(summary = "공지사항 등록 API")
    @PostMapping("/api/v1/admin/notice")
    public ResponseEntity<DataResponse> addNotice(
            @Valid @RequestBody NoticeCreateRequest request
    ) {
        Long noticeId = noticeService.save(request);
        return ResponseEntity.ok(DataResponse.send(noticeId));
    }

    @Operation(summary = "공지사항 수정 API")
    @PutMapping("/api/v1/admin/notice")
    public ResponseEntity<DataResponse> updateNotice(
            @Valid @RequestBody NoticeUpdateRequest request
    ) {
        Long noticeId = noticeService.update(request);
        return ResponseEntity.ok(DataResponse.send(noticeId));
    }


}
