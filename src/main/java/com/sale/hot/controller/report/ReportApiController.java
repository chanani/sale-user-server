package com.sale.hot.controller.report;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Report API Controller", description = "신고 관련 API를 제공합니다.")
@RestController(value = "/api/v1")
@RequiredArgsConstructor
public class ReportApiController {
}
