package com.sale.hot.domain.company.service;

import com.sale.hot.domain.company.repository.CompanyRepository;
import com.sale.hot.domain.company.service.dto.request.CompanyJoinRequest;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.company.Company;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultCompanyService implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public void companyJoin(CompanyJoinRequest request) {
        // 아이디 중복 여부 확인
        checkCompanyId(request.companyId());
        // 비밀번호 일치 여부 확인
        equalPassword(request.password(), request.passwordCheck());
        // 이메일 사용 여부 확인
        checkCompanyEmail(request.email());
        // 연락처 사용 여부 확인
        checkCompanyPhone(request.phone());
        // 사업자 존재 여부 확인 Todo 검증 API 사용해야됨

        // Entity로 변경
        Company newEntity = request.toEntity();
        companyRepository.save(newEntity);
    }

    /**
     * 기업 아이디 중복 체크(탈퇴한 아이디로 가입 불가)
     * 중복일 경우 바로 예외 발생
     *
     * @param companyId 기업 아이디
     */
    private void checkCompanyId(String companyId) {
        if (companyRepository.existsByCompanyId(companyId)) {
            throw new OperationErrorException(ErrorCode.EXISTS_COMPANY_ID);
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

    /**
     * 기업 연락처 중복 체크
     * 중복일 경우 바로 예외 발생
     *
     * @param phone 기업 연락처
     */
    private void checkCompanyPhone(String phone) {
        if (companyRepository.existsByPhoneAndStatus(phone, StatusType.ACTIVE)) {
            throw new OperationErrorException(ErrorCode.EXISTS_COMPANY_PHONE);
        }
    }

    /**
     * 기업 이메일 중복 체크
     * 중복일 경우 바로 예외 발생
     *
     * @param email 기업 이메일
     */
    private void checkCompanyEmail(String email) {
        if (companyRepository.existsByEmail(email)) {
            throw new OperationErrorException(ErrorCode.EXISTS_COMPANY_EMAIL);
        }
    }
}
