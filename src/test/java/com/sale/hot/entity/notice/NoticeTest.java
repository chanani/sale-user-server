package com.sale.hot.entity.notice;

import com.sale.hot.domain.notice.repository.NoticeRepository;
import com.sale.hot.entity.common.constant.BooleanYn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeTest {

    @Autowired
    NoticeRepository noticeRepository;

    @Test
    public void sNames() throws Exception {
        Notice notice = Notice.builder()
                .title("공지사항 제목입니다.2")
                .content("<p>공지사항 내용2</p>")
                .active(BooleanYn.N)
                .build();

        noticeRepository.save(notice);
    }

}