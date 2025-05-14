package com.sale.hot.domain.report.service;

import com.sale.hot.domain.report.repository.ReportRepository;
import com.sale.hot.domain.report.service.dto.request.ReportRequest;
import com.sale.hot.entity.common.constant.ReportType;
import com.sale.hot.entity.report.Report;
import com.sale.hot.entity.user.User;
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
public class DefaultReportService implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    @Transactional
    public Long addReport(ReportRequest request, User user) {
        // 동일한 내용에 대해 신고 내역이 있는지 체크
        boolean checkExist = reportRepository.existsByTypeAndTargetIdAndUserId(ReportType.forValue(request.type()), request.targetId(), user.getId());
        if (checkExist) {
            throw new OperationErrorException(ErrorCode.EXISTS_REPORT);
        }

        // 신고 사유 최소 10자 이상 입력
        if (request.reason().length() < 10) {
            throw new OperationErrorException(ErrorCode.MIN_LENGTH_REPORT);
        }

        // Entity 생성
        Report newEntity = request.toEntity(user);

        // 등록
        Report saveReport = reportRepository.save(newEntity);
        return saveReport.getId();
    }
}
