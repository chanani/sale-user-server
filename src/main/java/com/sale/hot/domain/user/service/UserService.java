package com.sale.hot.domain.user.service;

import com.sale.hot.domain.user.service.dto.request.JoinRequest;
import com.sale.hot.domain.user.service.dto.request.LoginRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;

public interface UserService {
    /**
     * 회원가입
     * @param request 회원가입 요청 객체
     * @return 저장된 고객 식별자
     */
    void join(JoinRequest request);

    /**
     * 사용자 로그인
     * @param request 로그인 요청 객체
     * @return accessToken, refreshToken
     */
    LoginResponse login(LoginRequest request) throws Exception;
}
