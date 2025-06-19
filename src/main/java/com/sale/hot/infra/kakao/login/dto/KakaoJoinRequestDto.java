package com.sale.hot.infra.kakao.login.dto;

import com.sale.hot.entity.common.constant.SocialType;
import com.sale.hot.entity.user.User;
import com.sale.hot.infra.common.dto.JoinRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class KakaoJoinRequestDto extends JoinRequestDto {

    @Schema(description = "인가 코드")
    @NotNull(message = "인가 코드는 필수입니다.")
    private String code;

    public User toEntity(KakaoUserInfoResponseDto userInfo) {
        return User.builder()
                .id(null)
                .email(userInfo.kakaoAccount.email)
                .name(this.getName())
                .gender(this.getGender())
                .birth(LocalDate.parse(this.getBirth()))
                .nickname(this.getNickname())
                .phone(this.getPhone())
                .socialType(SocialType.KAKAO)
                .socialId(String.valueOf(userInfo.id))
                .build();
    }
}
