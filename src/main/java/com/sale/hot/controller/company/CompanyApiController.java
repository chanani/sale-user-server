package com.sale.hot.controller.company;

import com.sale.hot.domain.company.service.CompanyService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company API Controller", description = "기업 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class CompanyApiController {

    private final CompanyService companyService;


}
