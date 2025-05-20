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


}
