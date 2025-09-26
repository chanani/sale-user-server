package com.sale.hot.infra.common.dto;

import com.sale.hot.entity.common.constant.Gender;
import com.sale.hot.global.regex.Regex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static com.sale.hot.global.util.CommonUtil.createNickName;

@Getter
public class JoinRequestDto {

    @Schema(description = "이름", example = "홍길동")
    @Pattern(regexp = Regex.KOREAN_NAME, message = "이름 형식을 확인해주세요.")
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Schema(description = "닉네임", example = "네고왕")
    @Pattern(regexp = Regex.KICKNAME, message = "닉네임 형식을 확인해주세요.")
    private String nickname;

    @Schema(description = "이메일", example = "xx@xx.xx")
    @Pattern(regexp = Regex.EMAIL, message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @Schema(description = "연락처", example = "01012341234")
    @Pattern(regexp = Regex.PHONE, message = "연락처 형식을 확인해주세요.")
    @NotBlank(message = "연락처는 필수입니다.")
    private String phone;

    @Schema(description = "성별(M,F)", example = "F")
    @NotNull(message = "성별은 필수입니다.")
    private Gender gender;

    @Schema(description = "생년월일(yyyy-MM-dd", example = "1995-07-10")
    @Pattern(regexp = Regex.BIRTHDAY, message = "생년월일 형식을 확인해주세요.")
    @NotNull(message = "생년월일은 필수입니다.")
    private String birth;

    public void updateNickName(){
        this.nickname = createNickName();
    }

}
