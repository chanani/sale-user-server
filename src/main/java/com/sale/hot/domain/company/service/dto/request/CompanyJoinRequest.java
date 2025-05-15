package com.sale.hot.domain.company.service.dto.request;

import com.sale.hot.entity.common.constant.Gender;
import com.sale.hot.entity.company.Company;
import com.sale.hot.global.regex.Regex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;


public record CompanyJoinRequest(
        @Schema(description = "아이디")
        @Pattern(regexp = Regex.ID, message = "아이디 형식을 확인해주세요.")
        @NotBlank(message = "아이디는 필수입니다.")
        String companyId,

        @Schema(description = "이메일", example = "aa@aa.aa")
        @Pattern(regexp = Regex.EMAIL, message = "이메일 형식을 확인해주세요.")
        @NotBlank(message = "이메일은 필수입니다.")
        String email,

        @Schema(description = "비밀번호", example = "zxc123!@#")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @Schema(description = "비밀번호 확인", example = "zxc123!@#")
        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        String passwordCheck,

        @Schema(description = "이름", example = "홍길동")
        @Pattern(regexp = Regex.KOREAN_NAME, message = "이름 형식을 확인해주세요.")
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "연락처", example = "01012341234")
        @Pattern(regexp = Regex.PHONE, message = "연락처 형식을 확인해주세요.")
        String phone,

        @Schema(description = "성별(M,F)", example = "F")
        @NotNull(message = "성별은 필수입니다.")
        Gender gender,

        @Schema(description = "생년월일(yyyy-MM-dd", example = "1995-07-10")
        @Pattern(regexp = Regex.BIRTHDAY, message = "생년월일 형식을 확인해주세요.")
        @NotBlank(message = "생년월일은 필수입니다.")
        String birth,

        @Schema(description = "사업자번호")
        @Pattern(regexp = Regex.BUSINESS_NUMBER, message = "사업자번호 형식을 확인해주세요.")
        @NotBlank(message = "사업자번호는 필수입니다.")
        String businessNumber,

        @Schema(description = "상호명")
        @NotBlank(message = "상호명은 필수입니다.")
        String companyName,

        @Schema(description = "업태/업종")
        @NotBlank(message = "업태/업종은 필수입니다.")
        String businessType,

        @Schema(description = "사업장 주소")
        @NotBlank(message = "사업장 주소는 필수입니다.")
        String address,

        @Schema(description = "우편번호")
        @NotBlank(message = "우편번호는 필수입니다.")
        String zipcode
) {

        public Company toEntity(){
                return Company.builder()
                        .companyId(this.companyId)
                        .password(new BCryptPasswordEncoder().encode(this.password))
                        .name(this.name)
                        .phone(this.phone)
                        .email(this.email)
                        .gender(this.gender)
                        .birth(LocalDate.parse(this.birth))
                        .businessNumber(this.businessNumber)
                        .businessType(this.businessType)
                        .companyName(this.companyName)
                        .address(this.address)
                        .zipcode(this.zipcode)
                        .build();
        }
}
