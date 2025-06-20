package com.sale.hot.domain.user.service;

import com.sale.hot.domain.user.service.dto.request.JoinRequest;
import com.sale.hot.domain.user.service.dto.request.LoginRequest;
import com.sale.hot.domain.user.service.dto.request.UserUpdatePasswordRequest;
import com.sale.hot.domain.user.service.dto.request.UserUpdateRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.domain.user.service.dto.response.UserInfoResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.infra.kakao.login.dto.KakaoJoinRequestDto;
import com.sale.hot.infra.kakao.login.dto.KakaoLoginRequestDto;
import com.sale.hot.infra.kakao.login.dto.KakaoMergeRequestDto;
import com.sale.hot.infra.naver.login.dto.NaverJoinRequestDto;
import com.sale.hot.infra.naver.login.dto.NaverMergeRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * 회원가입
     *
     * @param request 회원가입 요청 객체
     */
    void join(JoinRequest request);

    /**
     * 사용자 로그인
     *
     * @param request 로그인 요청 객체
     * @return accessToken, refreshToken 객체 반환
     */
    LoginResponse login(LoginRequest request) throws Exception;

    /**
     * 회원정보 수정
     *
     * @param request 회원정보 수정 요청 객체
     * @param user    로그인 회원 객체
     */
    void updateUser(UserUpdateRequest request, User user);

    /**
     * 회원 비밀번호 수정
     *
     * @param request 비밀번호 수정 요청 객체
     * @param user    로그인 회원 객체
     */
    void updateUserPassword(UserUpdatePasswordRequest request, User user);

    /**
     * 회원 정보 조회
     *
     * @param user 로그인 사용자 객체
     * @return 로그인 회원 객체
     */
    UserInfoResponse getInfo(User user);

    /**
     * 카카오 간편 회원가입
     *
     * @param request 회원가입 요청 객체
     */
    void kakaoJoin(KakaoJoinRequestDto request);

    /**
     * 카카오 로그인
     *
     * @param request 로그인 요청 객체
     * @return accessToken, refreshToken 객체 반환
     */
    LoginResponse kakaoLogin(KakaoLoginRequestDto request) throws Exception;

    /**
     * 회원 프로필 이미지 변경
     *
     * @param inputFile 이미지 파일 객체
     * @param user      로그인 회원 객체
     */
    void updateProfile(MultipartFile inputFile, User user);

    /**
     * 회원 카카오 계정 연동
     *
     * @param request 계정 연동 요청 객체
     * @param user    로그인 회원 객체
     */
    void kakaoMerge(KakaoMergeRequestDto request, User user);

    /**
     * 네이버 간편 회원가입
     *
     * @param request 회원가입 요청 객체
     */
    void naverJoin(NaverJoinRequestDto request);

    /**
     * 네이버 로그인
     *
     * @param request 로그인 요청 객체
     * @return accessToken, refreshToken 객체 반환
     */
    LoginResponse naverLogin(@Valid KakaoLoginRequestDto request) throws Exception;

    /**
     * 회원 네이버 계정 연동
     *
     * @param request 계정 연동 요청 객체
     * @param user    로그인 회원 객체
     */
    void naverMerge(@Valid NaverMergeRequestDto request, User user);
}
