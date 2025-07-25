package com.sale.hot.domain.grade.service;

import com.sale.hot.domain.grade.service.dto.response.GradeResponse;
import com.sale.hot.entity.user.User;

import java.util.List;

public interface GradeService {
    /**
     * 등급 목록 조회
     * @return 등급 리스트
     */
    List<GradeResponse> getGrades();

    /**
     * 등급 등업
     */
    String upgradeGrade(User user);
}
