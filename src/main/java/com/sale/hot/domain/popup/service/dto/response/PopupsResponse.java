package com.sale.hot.domain.popup.service.dto.response;

import com.sale.hot.entity.popup.Popup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PopupsResponse {

    @Schema(description = "팝업 고유번호")
    private Long id;

    @Schema(description = "팝업 제목")
    private String title;

    @Schema(description = "팝업 내용")
    private String content;

    @Schema(description = "팝업 링크")
    private String link;

    @Schema(description = "작성일")
    private LocalDateTime createAt;

    public PopupsResponse(Popup popup) {
        this.id = popup.getId();
        this.title = popup.getTitle();
        this.content = popup.getContent();
        this.link = popup.getLink();
        this.createAt = popup.getCreatedAt();
    }

}
