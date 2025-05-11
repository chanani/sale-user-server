package com.sale.hot.domain.keyword.service;

import com.sale.hot.domain.keyword.service.dto.request.KeywordCreateRequest;
import com.sale.hot.entity.user.User;
import jakarta.validation.Valid;

public interface KeywordService {

    /**
     * 알림 받을 키워드 등록
     * @param request 키워드 등록 요청 객체
     * @param user 로그인 사용자 객체
     */
    void addKeyword(@Valid KeywordCreateRequest request, User user);
}
