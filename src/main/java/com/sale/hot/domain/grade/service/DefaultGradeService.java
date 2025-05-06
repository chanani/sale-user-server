package com.sale.hot.domain.grade.service;

import com.sale.hot.domain.grade.repository.GradeRepository;
import com.sale.hot.domain.grade.service.dto.response.GradeResponse;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.grade.Grade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultGradeService implements GradeService{

    private final GradeRepository gradeRepository;

    @Override
    public List<GradeResponse> getGrades() {
        return gradeRepository.findByStatusOrderByRankingAsc(StatusType.ACTIVE).stream()
                .map(GradeResponse::new)
                .toList();
    }
}
