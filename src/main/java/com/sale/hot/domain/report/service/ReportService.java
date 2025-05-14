package com.sale.hot.domain.report.service;

import com.sale.hot.domain.report.service.dto.request.ReportRequest;
import com.sale.hot.entity.user.User;
import jakarta.validation.Valid;

public interface ReportService {
    /**
     * 신고 등록
     * @param request 신고 요청 객체
     * @param user 로그인 사용자 객체
     * @return 신고 식별자
     */
    Long addReport(@Valid ReportRequest request, User user);
}
