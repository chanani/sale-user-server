package com.sale.hot.controller.notice;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notice API Controller", description = "공지사항 관련 API를 제공합니다.")
@RestController(value = "/api/v1")
@RequiredArgsConstructor
public class NoticeApiController {
}
