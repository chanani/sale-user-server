package com.sale.hot.domain.post.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PostCompanyResponse {

    @Schema(description = "기업 ID")
    private Long companyId;

    @Schema(description = "기업명")
    private String companyName;

//    @Schema(description = "프로필 이미지")
//    private String profile;

    public PostCompanyResponse(Long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

}
