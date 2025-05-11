package com.sale.hot.domain.keyword.service.dto.response;

import com.sale.hot.entity.keyword.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class KeywordResponse {

    @Schema(description = "키워드명")
    private String name;

    public KeywordResponse(Keyword keyword) {
        this.name = keyword.getName();
    }
}
