package com.sale.hot.domain.user.service.dto.request;

import com.sale.hot.entity.common.constant.Gender;
import com.sale.hot.entity.common.constant.SocialType;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.regex.Regex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

import static com.sale.hot.global.util.CommonUtil.createNickName;

public record JoinRequest(
        @Schema(description = "아이디", example = "testId1")
        @Pattern(regexp = Regex.ID, message = "아이디 형식을 확인해주세요.")
        @NotBlank(message = "이이디는 필수입니다.")
        String userId,

        @Schema(description = "비밀번호", example = "zxc123!@#")
        @Pattern(regexp = Regex.PASSWORD, message = "비밀번호 형식을 확인해주세요.")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @Schema(description = "비밀번호 확인", example = "zxc123!@#")
        @Pattern(regexp = Regex.PASSWORD, message = "비밀번호 형식을 확인해주세요.")
        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        String passwordCheck,

        @Schema(description = "이름", example = "홍길동")
        @Pattern(regexp = Regex.KOREAN_NAME, message = "이름 형식을 확인해주세요.")
        @NotEmpty(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "닉네임", example = "네고왕")
        @Pattern(regexp = Regex.KICKNAME, message = "닉네임 형식을 확인해주세요.")
        String nickname,

        @Schema(description = "연락처", example = "01012341234")
        @Pattern(regexp = Regex.PHONE, message = "연락처 형식을 확인해주세요.")
        @NotBlank(message = "연락처는 필수입니다.")
        String phone,

        @Schema(description = "이메일", example = "aa@aa.aa")
        @Pattern(regexp = Regex.EMAIL, message = "이메일 형식을 확인해주세요.")
        @NotBlank(message = "이메일은 필수입니다.")
        String email,

        @Schema(description = "성별(M,F)", example = "F")
        @NotNull(message = "성별은 필수입니다.")
        Gender gender,

        @Schema(description = "생년월일(yyyy-MM-dd", example = "1995-07-10")
        @Pattern(regexp = Regex.BIRTHDAY, message = "생년월일 형식을 확인해주세요.")
        @NotBlank(message = "생년월일은 필수입니다.")
        String birth,

        @Schema(description = "회원가입 타입(LOCAL, KAKAO, NAVER, GOOGLE)", example = "LOCAL")
        SocialType socialType,

        @Schema(description = "소셜 아이디")
        String socialId
) {
    // Entity 생성 시 비밀번호 암호화 진행
    public User toEntity() {
        return User.builder()
                .userId(this.userId)
                .password(new BCryptPasswordEncoder().encode(this.password))
                .name(this.name)
                .nickname(StringUtils.hasText(this.nickname) ? this.nickname : createNickName())
                .phone(this.phone)
                .email(this.email)
                .gender(this.gender)
                .birth(LocalDate.parse(this.birth))
                .socialType(this.socialType)
                .socialId(this.socialId)
                .build();
    }

}
