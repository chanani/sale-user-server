package com.sale.hot.domain.keyword.service;

import com.sale.hot.domain.keyword.service.dto.request.KeywordCreateRequest;
import com.sale.hot.domain.keyword.service.dto.response.KeywordResponse;
import com.sale.hot.entity.user.User;
import jakarta.validation.Valid;

import java.util.List;

public interface KeywordService {

    /**
     * 알림 받을 키워드 등록
     * @param request 키워드 등록 요청 객체
     * @param user 로그인 사용자 객체
     */
    void addKeyword(@Valid KeywordCreateRequest request, User user);

    /**
     * 알림 받을 키워드 삭제
     * @param keywordId 키워드 식별자
     * @param user 로그인 사용자 객체
     */
    void deleteKeyword(Long keywordId, User user);

    /**
     * 알림 받을 키워드 목록 조회
     * @param user 로그인 사용자 객체
     * @return 알림 받을 키워드 리스트
     */
    List<KeywordResponse> getKeywords(User user);
}
