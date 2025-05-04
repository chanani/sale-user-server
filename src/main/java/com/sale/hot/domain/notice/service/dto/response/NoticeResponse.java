package com.sale.hot.domain.notice.service.dto.response;

import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.notice.Notice;
import com.sale.hot.global.util.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeResponse {
    @Schema(description = "공지사항 ID")
    private Long id;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "상단 고정 여부")
    private BooleanYn pin;

    @Schema(description = "활성화 여부")
    private BooleanYn active;

    @Schema(description = "조회수")
    private Integer viewCount;

    @Schema(description = "작성일")
    private String createdAt;

    public NoticeResponse(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.pin = notice.getPin();
        this.active = notice.getActive();
        this.viewCount = notice.getViewCount();
        this.createdAt = DateUtil.localDateTimeTolocalDateTimeString(notice.getCreatedAt());
    }
}
