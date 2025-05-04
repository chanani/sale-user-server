package com.sale.hot.domain.notice.repository;

import com.sale.hot.domain.notice.repository.condition.NoticeCondition;
import com.sale.hot.entity.notice.Notice;
import com.sale.hot.global.page.Pageable;

import java.util.List;

public interface NoticeRepositoryCustom {
    /**
     * 공지사항 검색 토탈 수
     * @param condition 검색 조건
     * @return 검색된 공지사항 토탈 수
     */
    Long countQuery(NoticeCondition condition);

    /**
     * 공지사항 리스트 조회
     * @param condition 검색 조건
     * @param pageable 페이징 객체
     * @return 페이징 된 공지사항 리스트
     */
    List<Notice> findQuery(NoticeCondition condition, Pageable pageable);
}
