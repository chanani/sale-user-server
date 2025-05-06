package com.sale.hot.domain.operator.service;

import com.sale.hot.domain.operator.service.dto.request.OperatorJoinRequest;
import com.sale.hot.domain.operator.service.dto.request.OperatorLoginRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.entity.operator.Operator;

public interface OperatorService {
    /**
     * 관리자 로그인
     * @param request 로그인 요청 객체
     * @return accessToken, refreshToken 객체 반환
     */
    LoginResponse login(OperatorLoginRequest request) throws Exception;

    /**
     * 관리자 계정 추가
     * @param request 계정 추가 요청 객체
     * @param operator 로그인 관리자 객체
     */
    void join(OperatorJoinRequest request, Operator operator);
}
