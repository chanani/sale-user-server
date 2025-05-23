package com.sale.hot.entity.common.constant;

/**
 * 회원가입 종류
 */
public enum SocialType {
    LOCAL("포털"),
    KAKAO("카카오"),
    NAVER("네이버"),
    GOOGLE("구글");

    private final String value;

    SocialType(String value) {
        this.value = value;
    }
}
