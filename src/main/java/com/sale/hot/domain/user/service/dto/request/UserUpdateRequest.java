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

import java.time.LocalDate;

public record UserUpdateRequest(
        @Schema(description = "이름", example = "홍길동")
        @Pattern(regexp = Regex.KOREAN_NAME, message = "이름 형식을 확인해주세요.")
        @NotEmpty(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "닉네임", example = "네고왕")
        @Pattern(regexp = Regex.KICKNAME, message = "닉네임 형식을 확인해주세요.")
        @NotEmpty(message = "닉네임은 필수입니다.")
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
        String birth

) {
        public User toEntity(){
                return User.builder()
                        .name(this.name)
                        .nickname(this.nickname)
                        .phone(this.phone)
                        .email(this.email)
                        .gender(this.gender)
                        .birth(LocalDate.parse(this.birth))
                        .build();
        }

}
