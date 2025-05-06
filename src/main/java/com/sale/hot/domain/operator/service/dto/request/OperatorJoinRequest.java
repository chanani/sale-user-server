package com.sale.hot.domain.operator.service.dto.request;

import com.sale.hot.entity.common.constant.Gender;
import com.sale.hot.entity.operator.Operator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

public record OperatorJoinRequest(
        @Schema(description = "아이디", example = "***REMOVED***12")
        @NotBlank(message = "아이디는 필수입니다.")
        String operatorId,

        @Schema(description = "비밀번호", example = "zxc123!@#")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @Schema(description = "비밀번호 확인", example = "zxc123!@#")
        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        String passwordCheck,

        @Schema(description = "이름", example = "관리자")
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "성별", example = "M")
        @NotNull(message = "성별은 필수입니다.")
        Gender gender,

        @Schema(description = "생년월일", example = "1995-07-10")
        @NotBlank(message = "생년월일은 필수입니다.")
        String birth
) {
        // Entity 생성 시 비밀번호 암호화 진행
        public Operator toEntity(){
                return Operator.builder()
                        .operatorId(this.operatorId)
                        .password(new BCryptPasswordEncoder().encode(this.password))
                        .name(this.name)
                        .gender(this.gender)
                        .birth(LocalDate.parse(this.birth))
                        .build();

        }
}
