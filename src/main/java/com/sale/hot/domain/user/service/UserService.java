package com.sale.hot.domain.user.service;

import com.sale.hot.domain.user.service.dto.request.JoinRequest;
import com.sale.hot.domain.user.service.dto.request.LoginRequest;
import com.sale.hot.domain.user.service.dto.request.UserUpdatePasswordRequest;
import com.sale.hot.domain.user.service.dto.request.UserUpdateRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.entity.user.User;

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
     * @return accessToken, refreshToken 객체 반환
     */
    LoginResponse login(LoginRequest request) throws Exception;

    /**
     * 회원정보 수정
     * @param request 회원정보 수정 요청 객체
     * @param user 로그인 회원 객체
     */
    void updateUser(UserUpdateRequest request, User user);

    /**
     * 회원 비밀번호 수정
     * @param request 비밀번호 수정 요청 객체
     * @param user 로그인 회원 객체
     */
    void updateUserPassword(UserUpdatePasswordRequest request, User user);
}
