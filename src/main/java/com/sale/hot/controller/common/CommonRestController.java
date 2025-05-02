package com.sale.hot.controller.common;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Common API Controller", description = "공통으로 사용하는 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class CommonRestController {
}
