package com.sale.hot.controller.report;

import com.sale.hot.domain.report.service.ReportService;
import com.sale.hot.domain.report.service.dto.request.ReportRequest;
import com.sale.hot.domain.user.service.dto.response.UserInfoResponse;
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

@Tag(name = "Report API Controller", description = "신고 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class ReportApiController {

    private final ReportService reportService;

    @Operation(summary = "신고 등록 API",
            description = """
                    회원, 게시물, 댓글을 신고합니다.
                    신고 사유는 10자 이상 입력해주세요.
                    """)
    @PostMapping("/api/v1/user/report")
    public ResponseEntity<ApiResponse> addReport(
            @Valid @RequestBody ReportRequest request,
            @Parameter(hidden = true) User user
    ) {
        reportService.addReport(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
