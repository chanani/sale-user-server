package com.sale.hot.domain.attend.service;

import com.sale.hot.entity.user.User;

import java.time.LocalDate;

public interface AttendService {
    /**
     * 출석 등록
     * @param user 로그인 사용자 객체
     * @param now 현재 년/월/일
     */
    void save(User user, LocalDate now);
}
