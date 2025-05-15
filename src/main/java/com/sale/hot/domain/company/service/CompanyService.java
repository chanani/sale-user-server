package com.sale.hot.domain.company.service;

import com.sale.hot.domain.company.service.dto.request.CompanyJoinRequest;
import jakarta.validation.Valid;

public interface CompanyService {
    /**
     * 기업 회원가입
     * @param request 기업 회원가입 요청 객체
     */
    void companyJoin(CompanyJoinRequest request);
}
