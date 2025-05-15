package com.sale.hot.controller.company;

import com.sale.hot.domain.company.service.CompanyService;
import com.sale.hot.domain.company.service.dto.request.CompanyJoinRequest;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company API Controller", description = "기업 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class CompanyApiController {

    private final CompanyService companyService;

    @Operation(summary = "기업용 회원가입 API",
            description = """
                    기업용 회원가입 입니다.
                    생년월일 형식 : yyyy-MM-dd
                    성별 : M, F
                    연락처 형식 : 01000000000
                    사업자번호 형식 : 000-00-00000
                    """)
    @NoneAuth
    @PostMapping("/api/v1/none/company-join")
    public ResponseEntity<ApiResponse> companyJoin(
            @Valid @RequestBody CompanyJoinRequest request
    ) {
        companyService.companyJoin(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
