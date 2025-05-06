package com.sale.hot.domain.grade.service;

import com.sale.hot.domain.grade.repository.GradeRepository;
import com.sale.hot.domain.grade.service.dto.request.GradeCreateRequest;
import com.sale.hot.domain.grade.service.dto.request.GradeUpdateRequest;
import com.sale.hot.domain.grade.service.dto.response.GradeResponse;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.grade.Grade;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultGradeService implements GradeService {

    private final GradeRepository gradeRepository;

    @Override
    public List<GradeResponse> getGrades() {
        return gradeRepository.findByStatusOrderByRankingAsc(StatusType.ACTIVE).stream()
                .map(GradeResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public void addGrade(GradeCreateRequest request) {
        // 등급명 중복 체크
        checkGradeName(request.name());

        // 순서 조회(제일 마지막 순번으로 등록)
        Integer ranking = gradeRepository.findMaxRanking(StatusType.ACTIVE).orElse(0);

        // 등급 등록
        Grade newEntity = request.toEntity(++ranking);
        Grade saveGrade = gradeRepository.save(newEntity);
    }

    @Override
    @Transactional
    public void updateGrade(Long gradeId, GradeUpdateRequest request) {
        Grade findGrade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_GRADE));

        // 등급명 중복 체크(해당 레코드 제외)
        checkGradeNameNeId(gradeId, request.name());

        Grade newGrade = request.toEntity(findGrade.getRanking());
        findGrade.update(newGrade);
    }



    @Override
    @Transactional
    public void deleteGrade(Long gradeId) {
        Grade findGrade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_GRADE));
        findGrade.remove();
    }

    /**
     * 등급명 중복 체크
     * 중복일 경우 바로 예외 발생
     *
     * @param name 등급명
     */
    private void checkGradeName(String name) {
        if (gradeRepository.existsByNameAndStatus(name, StatusType.ACTIVE)) {
            throw new OperationErrorException(ErrorCode.EXISTS_GRADE_NAME);
        }
    }

    /**
     * 등급명 중복 체크(해당 레코드 제외)
     * 중복일 경우 바로 예외 발생
     *
     * @param name 등급명
     */
    private void checkGradeNameNeId(Long gradeId, String name) {
        if (gradeRepository.existsByNameAndStatusAndIdNot(name, StatusType.ACTIVE, gradeId)) {
            throw new OperationErrorException(ErrorCode.EXISTS_GRADE_NAME);
        }
    }
}
