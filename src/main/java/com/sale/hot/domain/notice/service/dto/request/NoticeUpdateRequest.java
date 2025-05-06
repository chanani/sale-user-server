package com.sale.hot.domain.notice.service.dto.request;

import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.notice.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NoticeUpdateRequest(
        @Schema(description = "공지사항 고유번호")
        @NotNull(message = "공지사항 번호는 필수입니다.")
        Long id,

        @Schema(description = "제목")
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @Schema(description = "내용")
        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @Schema(description = "활성화 여부")
        @NotNull(message = "활성화 여부는 필수입니다.")
        Boolean active
) {

    public Notice toEntity(){
        return Notice.builder()
                .title(this.title)
                .content(this.content)
                .active(this.active ? BooleanYn.Y : BooleanYn.N)
                .build();
    }
}
