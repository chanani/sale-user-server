package com.sale.hot.domain.notice.service;

import com.sale.hot.controller.notice.input.NoticesInput;
import com.sale.hot.domain.notice.repository.NoticeRepository;
import com.sale.hot.domain.notice.repository.condition.NoticeCondition;
import com.sale.hot.domain.notice.service.dto.response.NoticeResponse;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;
import com.sale.hot.global.page.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultNoticeService implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public Page<List<NoticeResponse>> getNotices(NoticesInput input, PageInput pageInput) {
        NoticeCondition condition = input.toCondition();
        // 해당 조건의 공지사항 수 조회
        Long totalCount = noticeRepository.countQuery(condition);
        // pageable 객체 생성
        Pageable pageable = new Pageable(pageInput.page(), totalCount.intValue(), pageInput.size());
        // 해당 조건의 공지사항 리스트 조회
        List<NoticeResponse> notices = noticeRepository.findQuery(condition, pageable).stream()
                .map(NoticeResponse::new)
                .toList();
        return new com.sale.hot.global.page.Page<>(pageable, notices);
    }
}
