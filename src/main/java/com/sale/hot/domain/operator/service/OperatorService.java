package com.sale.hot.domain.operator.service;

import com.sale.hot.domain.operator.service.dto.request.OperatorLoginRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;

public interface OperatorService {
    /**
     * 관리자 로그인
     * @param request 로그인 요청 객체
     * @return accessToken, refreshToken 객체 반환
     */
    LoginResponse login(OperatorLoginRequest request) throws Exception;
}
