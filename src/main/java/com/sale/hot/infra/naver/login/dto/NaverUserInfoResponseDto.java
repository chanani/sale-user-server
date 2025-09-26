package com.sale.hot.infra.naver.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NaverUserInfoResponseDto {
    // API 호출 결과 코드
    @JsonProperty("code")
    public String code;

    // 호출 결과 메시지
    @JsonProperty("message")
    public String message;

    // 회원 식별 고유값
    @JsonProperty("id")
    public String id;

    // 사용자 별명
    @JsonProperty("nickname")
    public Integer nickname;

    // 사용자 이름
    @JsonProperty("name")
    public String name;

    // 사용자 메일 주소
    @JsonProperty("email")
    public String email;

    // 성별(F : 여성, M 남성, U : 확인 불가)
    @JsonProperty("gender")
    public String gender;

    // 연령대
    @JsonProperty("age")
    public String age;

    // 출생년도
    @JsonProperty("birthyear")
    public String birthyear;

    // 사용자 생일(MM-DD)
    @JsonProperty("birthday")
    public String birthday;

    // 프로필 이미지
    @JsonProperty("profile_image")
    public String profileImage;

    // 휴대전화번호
    @JsonProperty("mobile")
    public String mobile;

}
