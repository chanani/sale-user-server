package com.sale.hot.domain.grade.service;

import com.sale.hot.domain.grade.service.dto.request.GradeCreateRequest;
import com.sale.hot.domain.grade.service.dto.response.GradeResponse;

import java.util.List;

public interface GradeService {
    /**
     * 등급 목록 조회
     * @return 등급 리스트
     */
    List<GradeResponse> getGrades();

    /**
     * 등급 생성
     * @param request 등급 생성 요청 객체
     */
    void addGrade(GradeCreateRequest request);
}
