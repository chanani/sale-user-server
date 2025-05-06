package com.sale.hot.domain.notice.service;

import com.sale.hot.controller.notice.input.NoticesInput;
import com.sale.hot.domain.notice.service.dto.request.NoticeCreateRequest;
import com.sale.hot.domain.notice.service.dto.request.NoticeUpdateRequest;
import com.sale.hot.domain.notice.service.dto.response.NoticeResponse;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;

import java.util.List;

public interface NoticeService {
    /**
     * 공지사항 리스트 조회
     * @param input 검색 조건
     * @param pageInput 페이지 정보
     * @return 페이징 된 공지사항 리스트
     */
    Page<List<NoticeResponse>> getNotices(NoticesInput input, PageInput pageInput);

    /**
     * 공지사항 조회수 증가
     * @param id 공지사항 식별자
     */
    void plusNoticeViewCount(Long id);

    /**
     * 공지사항 등록
     * @param request 공지사항 등록 요청 객체
     * @return 생성된 공지사항 식별자
     */
    Long save(NoticeCreateRequest request);

    /**
     * 공지사항 수정
     * @param noticeId 공지사항 식별자
     * @param request 공지사항 수정 요청 객체
     */
    void updateNotice(Long noticeId, NoticeUpdateRequest request);

    /**
     * 공지사항 삭제
     * @param noticeId 공지사항 식별자
     */
    void deleteNotice(Long noticeId);
}
