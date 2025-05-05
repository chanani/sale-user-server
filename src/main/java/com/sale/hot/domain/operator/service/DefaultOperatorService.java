package com.sale.hot.domain.operator.service;

import com.sale.hot.domain.operator.repository.OperatorRepository;
import com.sale.hot.domain.operator.service.dto.request.OperatorLoginRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.common.constant.UserType;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultOperatorService implements OperatorService{

    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    @Override
    public LoginResponse login(OperatorLoginRequest request) throws Exception {
        // 아이디로 정보 조회
        Operator operator = operatorRepository.findByOperatorIdAndStatus(request.operatorId(), StatusType.ACTIVE)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_EQUAL_ID_PASSWORD));

        // 비밀번호 일치 여부 조회
       /* if (!passwordEncoder.matches(request.password(), operator.getPassword())) {
            throw new OperationErrorException(ErrorCode.NOT_EQUAL_ID_PASSWORD);
        }*/

        // accessToken 발급
        String accessToken = jwtProvider.createAccessToken(operator.getId(), UserType.OPERATOR);
        // refreshToken 발급
        String refreshToken = jwtProvider.createRefreshToken(operator.getId(), UserType.OPERATOR);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
