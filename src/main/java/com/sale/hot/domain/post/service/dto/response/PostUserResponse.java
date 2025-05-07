package com.sale.hot.domain.post.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PostUserResponse {

    @Schema(description = "회원 ID")
    private Long userId;

    @Schema(description = "회원 닉네임")
    private String nickname;

    @Schema(description = "프로필 이미지")
    private String profile;

    public PostUserResponse(Long userId, String nickname, String profile) {
        this.userId = userId;
        this.nickname = nickname;
        this.profile = profile;
    }
}
