package com.sale.hot.domain.operator.service;

import com.sale.hot.domain.operator.repository.OperatorRepository;
import com.sale.hot.domain.operator.service.dto.request.OperatorJoinRequest;
import com.sale.hot.domain.operator.service.dto.request.OperatorLoginRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.common.constant.UserType;
import com.sale.hot.entity.grade.Grade;
import com.sale.hot.entity.operator.Operator;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import com.sale.hot.global.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultOperatorService implements OperatorService {

    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    @Override
    @Transactional
    public LoginResponse login(OperatorLoginRequest request) throws Exception {
        // 아이디로 정보 조회
        Operator operator = operatorRepository.findByOperatorIdAndStatus(request.operatorId(), StatusType.ACTIVE)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_EQUAL_ID_PASSWORD));

        // 비밀번호 일치 여부 조회
        if (!passwordEncoder.matches(request.password(), operator.getPassword())) {
            throw new OperationErrorException(ErrorCode.NOT_EQUAL_ID_PASSWORD);
        }

        // accessToken 발급
        String accessToken = jwtProvider.createAccessToken(operator.getId(), UserType.OPERATOR);
        // refreshToken 발급
        String refreshToken = jwtProvider.createRefreshToken(operator.getId(), UserType.OPERATOR);

        // 최근 접속일 정보 업데이트
        operator.updateLastVisit();

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void join(OperatorJoinRequest request, Operator operator) {
        // 아이디 중복 검사
        checkOperatorId(request.operatorId());
        // 비밀번호 일치 여부 확인
        equalPassword(request.password(), request.passwordCheck());
        // Entity로 변환
        Operator newOperator = request.toEntity();
        // 회원가입
        Operator saveOperator = operatorRepository.save(newOperator);
        log.info("join success ! operator id = {}", saveOperator.getOperatorId());
    }

    /**
     * 회원 아이디 중복 체크(탈퇴한 아이디로 가입 불가)
     * 중복일 경우 바로 예외 발생
     *
     * @param operatorId 관리자 아이디
     */
    private void checkOperatorId(String operatorId) {
        if (operatorRepository.existsByOperatorId(operatorId)) {
            throw new OperationErrorException(ErrorCode.EXISTS_USER_ID);
        }
    }

    /**
     * 비밀번호 동일 여부 체크
     * 동일하지 않을 경우 예외 발생
     *
     * @param password      비밀번호
     * @param passwordCheck 확인 비밀번호
     */
    private void equalPassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new OperationErrorException(ErrorCode.NOT_EQUAL_PASSWORD);
        }
    }
}
